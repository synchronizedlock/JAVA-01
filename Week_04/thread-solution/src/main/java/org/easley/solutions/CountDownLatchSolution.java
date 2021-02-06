package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.o
 */
public class CountDownLatchSolution extends AbstractSolution {

    public static void main(String[] args) {
        new CountDownLatchSolution().solute();
    }

    @Override
    protected void generateResult() {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            result = FibonacciUtils.getFibonacci(16);
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
