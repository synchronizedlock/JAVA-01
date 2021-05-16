package org.easley.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActivemqConsumer {

    @JmsListener(destination = "test-queue")
    public void receiveMsg(String text) {
        System.out.println("接收到消息 : "+text);
    }

    @JmsListener(destination = "test-topic")
    public void receiveTopic1(String text) {
        System.out.println("receiveTopic1接收到Topic消息 : " + text);
    }
    @JmsListener(destination = "test-topic")
    public void receiveTopic2(String text) {
        System.out.println("receiveTopic2接收到Topic消息 : " + text);
    }
}
