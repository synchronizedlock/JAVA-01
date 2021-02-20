package org.easley.spring.beans;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 测试类：萌妹子
 *
 * @author Easley
 * @date 2021/2/19
 * @since 1.0
 */
@Data
public class MoeGirl implements Girl {

    /**
     * 姓名
     */
    private String name = "nameless";

    /**
     * 微信号
     */
    private String weChat = "noWeChat";

    /**
     * 亲密度阈值
     */
    private int intimacyThreshold = 100;

    @Override
    public String toString() {
        return "MoeGirl{" +
                "name='" + name + '\'' +
                ", weChat='" + weChat + '\'' +
                ", intimacyThreshold=" + intimacyThreshold +
                '}';
    }

    @Override
    public String getGirlName() {
        System.out.println(">>> 询问芳名ing~");
        return name;
    }

    @Override
    public String getWeChat() {
        System.out.println(">>> 求微信ing~");
        return weChat;
    }

    @Override
    public int chat(String name, String weChat, boolean gently) {
        System.out.println(">>> 聊天ing~");
        int intimacyIncrement = 0;

        // 有微信号的话，微信私聊亲密度增加更多
        if (!StringUtils.isEmpty(weChat)) {
            if (weChat.equals(this.weChat)) {
                intimacyIncrement += 20;
            } else {
                // 问可以不可以微信私聊，结果聊错了微信，没回人家，会减亲密度
                return -20;
            }
        } else {
            // 通过中间人传话聊天，亲密度获取会比较少
            intimacyIncrement += 10;
        }

        // 绅士点是加分项，反过来就无了
        if (!gently) {
            return -100000000;
        } else {
            intimacyIncrement += 20;
        }

        // 用名字称呼加分
        if (!StringUtils.isEmpty(name)) {
            if (name.equals(this.name)) {
                intimacyIncrement += 10;
            } else {
                // 名字叫错减分
                intimacyIncrement -= 10;
            }
        }
        return intimacyIncrement;
    }

    @Override
    public boolean makeAProposal(int annualSalary, int houseNum, int carNum, int bankAccountBalance, int intimacy) {
        System.out.println(">>> 当前亲密度：" + intimacy);
        System.out.println(">>> 求婚ing~");
        return intimacy >= intimacyThreshold;
    }
}
