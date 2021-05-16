package org.easley.kmq.v2.core;

public class KmqConsumer<T> {

    private final KmqBroker broker;
    private int atIndex;
    private Kmq kmq;

    public KmqConsumer(KmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.kmq = this.broker.findKmq(topic);
        if (null == kmq) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
    }

    public KmqMessage<T> poll(long timeout) throws InterruptedException {
        KmqMessage message = kmq.poll(timeout, atIndex);
        if (message != null) {
            System.out.printf("消费完成:" + message.toString());
            atIndex++;
        }
        return message;
    }
}
