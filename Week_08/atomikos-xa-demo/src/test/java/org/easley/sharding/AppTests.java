package org.easley.sharding;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.easley.sharding.entity.MallOrder;
import org.easley.sharding.service.MallOrderService;
import org.easley.sharding.utils.IdWorker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppBootstrap.class)
public class AppTests {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MallOrderService mallOrderService;

    @Test
    public void testXaSaveBatch() {
        // 保存前的数据量
        int totalBeforeSave = mallOrderService.list().size();
        try {
            // 这里插入第二条数据前会抛一个异常
            xaSaveBatch();
        } catch (Exception e) {}
        // 保存后的数据量
        int totalAfterSave =  mallOrderService.list().size();
        // 如果插入后数据总量变了，说明XA失败
        Assert.assertTrue(totalBeforeSave == totalAfterSave);
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void xaSaveBatch() {
        // 根据customerId取模存到不同的库
        MallOrder orderToMall0 = new MallOrder();
        orderToMall0.setId(idWorker.nextId());
        orderToMall0.setCustomerId(0L);
        mallOrderService.save(orderToMall0);

        int createException = 1/0;

        MallOrder orderToMall1 = new MallOrder();
        orderToMall1.setId(idWorker.nextId());
        orderToMall1.setCustomerId(1L);
        mallOrderService.save(orderToMall1);
    }
}
