package org.easley.activemq.producer;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

@Component
public class ActivemqProducer {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Resource
    private Queue queue;
    public void sendMsg(String msg) {
        System.out.println("发送消息内容 :" + msg);
        this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
    }

    @Resource
    private Topic topic;
    public void sendTopic(String msg) {
        System.out.println("发送Topic消息内容 :"+msg);
        this.jmsMessagingTemplate.convertAndSend(this.topic, msg);
    }
}
