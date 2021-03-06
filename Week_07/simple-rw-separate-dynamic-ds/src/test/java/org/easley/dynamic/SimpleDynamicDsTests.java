package org.easley.dynamic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.easley.dynamic.entity.MallOrder;
import org.easley.dynamic.service.MallOrderService;
import org.easley.dynamic.utils.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SimpleDynamicDsBootstrap.class)
public class SimpleDynamicDsTests {

    private static int processors = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * 雪花ID生成器
     */
    private IdGenerator idGenerator = IdGenerator.get();

    private ExecutorService pool = Executors.newFixedThreadPool(processors);

    @Autowired
    private DataSource dynamicDataSource;

    @Autowired
    private MallOrderService orderService;

    private final int[] SQL_PARAM_TYPES = {Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.BIGINT};
    private final String SQL_TEXT = "INSERT INTO mall_order (id, customer_id, original_total_price, deal_total_price) VALUES (?, ?, ?, ?)";

    /**
     * 测试原生JDBC插入耗时
     */
    @Test
    public void testRawJDBC() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CountDownLatch latch = new CountDownLatch(processors);
        int totalOrderAmount = 100_0000;
        for (int i = 0; i < processors; i++) {
            pool.submit(jdbcInsert(totalOrderAmount / processors, latch));
        }
        latch.await();
        pool.shutdown();

        System.out.println("总计耗时：" + stopWatch.getTotalTimeMillis() + " ms.");
    }

    private Runnable jdbcInsert(int amount, CountDownLatch latch) {
        return () -> {
            try (Connection connection = dynamicDataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_TEXT);
            ) {
                for (int i = 0; i < amount; i++) {
                    statement.setObject(1, UUID.randomUUID().toString().replaceAll("-", ""), SQL_PARAM_TYPES[0]);
                    statement.setObject(2, 123456L, SQL_PARAM_TYPES[1]);
                    statement.setObject(3, 10000L, SQL_PARAM_TYPES[2]);
                    statement.setObject(4, 1000L, SQL_PARAM_TYPES[3]);
                    statement.execute();
                }
                latch.countDown();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 测试MybatisPlus插入耗时
     */
    @Test
    public void testMybatisPlus() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CountDownLatch latch = new CountDownLatch(processors);
        int totalOrderAmount = 100_0000;
        for (int i = 0; i < processors; i++) {
            pool.submit(mybatisPlusInsert(totalOrderAmount / processors, latch));
        }
        latch.await();
        pool.shutdown();

        System.out.println("总计耗时：" + stopWatch.getTotalTimeMillis() + " ms.");
    }

    private Runnable mybatisPlusInsert(int amount, CountDownLatch latch) {
        return () -> {
            for (int i = 0; i < amount; i++) {
                orderService.save(createOrder());
            }
            latch.countDown();
        };
    }

    private MallOrder createOrder() {
        MallOrder order = new MallOrder();
        order.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setCustomerId(123456L);
        order.setOriginalTotalPrice(10000L);
        order.setDealTotalPrice(1000L);
        return order;
    }

    /**
     * 测试读写分离
     * 禁用同步后，select读从库，读不到写到主库的记录
     */
    @Test
    public void testReadFromSlave() {
        System.out.println(orderService.page(new Page<>()).getTotal());
    }
}
