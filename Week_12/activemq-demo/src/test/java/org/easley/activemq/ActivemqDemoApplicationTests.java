package org.easley.activemq;

import org.easley.activemq.producer.ActivemqProducer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivemqDemoApplicationTests {
    @Autowired
    private ActivemqProducer producer;

    @Test
    public void sendSimpleQueueMessage() {
        this.producer.sendMsg("提现200.00元");
    }

    @Test
    public void sendSimpleTopicMessage() {
        this.producer.sendTopic("提现200.00元");
    }
}
