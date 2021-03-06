package org.easley.dynamic.utils;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.toIntExact;

/**
 * 雪花ID生成器
 *
 * @author Easley
 * @date 2021/3/7
 * @since 1.0
 */
public class IdGenerator {

    private final long workerId;

    private final long datacenterId;

    private long sequence = 0L;

    /**
     * Thu, 04 Nov 2010 01:42:54 GMT
     */
    private final long twepoch = 1288834974657L;

    /**
     * 节点ID长度
     */
    private final long workerIdBits = 5L;

    /**
     * 数据中心ID长度
     */
    private final long datacenterIdBits = 5L;

    /**
     * 最大支持机器节点数0~31，一共32个
     */
    private final long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 最大支持数据中心节点数0~31，一共32个
     */
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);

    /**
     * 序列号12位
     */
    private final long sequenceBits = 12L;

    /**
     * 机器节点左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据中心节点左移17位
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间毫秒数左移22位
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 4095
     */
    private final long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public IdGenerator() {
        this(0L, 0L);
    }

    public IdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public static IdGenerator get() {
        return IdGenHolder.INSTANCE;
    }

    /**
     * 生成全局唯一订单号
     * 下单年月日6位 + 业务类型2位 + 时间戳加密数8位
     */
    public synchronized String nextOrderSn(Integer serviceType, String userId) {
        SimpleDateFormat formatShort = new SimpleDateFormat("yyMMdd");
        Date now = new Date();
        String currentDate = formatShort.format(now);
        //取nextId后8位
        DecimalFormat dfid = new DecimalFormat("00000000");
        String sequence = dfid.format(nextId() % 100000000);
        //对nextId进行混淆
        Long encSeq = encryptSequence(Long.valueOf(sequence), Integer.parseInt(userId) % 10);
        DecimalFormat dfst = new DecimalFormat("00");
        return currentDate + dfst.format(serviceType % 100) + encSeq;
    }

    private Long encryptSequence(Long sequence, Integer group) {
        long origin = sequence & ((1 << 25) - 1);
        int[] keys = new int[]{22133984, 59106757, 28837015, 49565802, 16370571, 23910123, 64893534, 87654830};
        long keyIndex = group % keys.length;
        int key = keys[toIntExact(keyIndex)];
        return origin ^ key;
    }

    /**
     * 生成全局唯一用户id
     * 格式：数字8 + 时间戳加密数8位
     */
    public synchronized String nextUserId() {
        //取nextId后8位
        DecimalFormat dfid = new DecimalFormat("00000000");
        String sequence = dfid.format(nextId() % 100000000);
        //对nextId进行混淆
        Long encSeq = encryptSequence(Long.valueOf(sequence), ThreadLocalRandom.current().nextInt());
        return "8" + encSeq;
    }

    private long nextId() {
        //获取当前毫秒数
        long timestamp = timeGen();
        //如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                //自旋等待到下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 最后按照规则拼出ID。
        // 000000000000000000000000000000000000000000  00000            00000       000000000000
        // time                                       datacenterId   workerId    sequence
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private static class IdGenHolder {
        private static final IdGenerator INSTANCE = new IdGenerator();
    }
}
