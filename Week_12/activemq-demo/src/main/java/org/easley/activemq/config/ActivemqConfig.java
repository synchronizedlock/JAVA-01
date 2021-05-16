package org.easley.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActivemqConfig {

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("test-topic");
    }
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("test-queue");
    }
}
