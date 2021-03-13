package org.easley.sharding;

import org.easley.sharding.entity.MallOrder;
import org.easley.sharding.service.MallOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppBootstrap.class)
public class AppTests {

    @Autowired
    private MallOrderService mallOrderService;

    @Test
    public void testSave() {
        MallOrder orderToMall0 = new MallOrder();
        orderToMall0.setCustomerId(0L);
        mallOrderService.save(orderToMall0);

        MallOrder orderToMall1 = new MallOrder();
        orderToMall1.setCustomerId(1L);
        mallOrderService.save(orderToMall1);

        System.out.println(mallOrderService.list());
    }

    @Test
    public void testSelect() {
        System.out.println(mallOrderService.getById(1370774190865096705L));
    }

    @Test
    public void testUpdate() {
        MallOrder order = new MallOrder();
        order.setId(1370774190865096705L);
        order.setStatus(1);

        mallOrderService.updateById(order);
        System.out.println(mallOrderService.getById(order.getId()));
    }

    @Test
    public void testDelete() {
        mallOrderService.removeById(1370774190865096705L);
        System.out.println(mallOrderService.getById(1370774190865096705L));
    }
}
