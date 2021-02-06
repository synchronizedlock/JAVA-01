package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueue实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class ArrayBlockingQueueSolution extends AbstractSolution {

    public static void main(String[] args) {
        new ArrayBlockingQueueSolution().solute();
    }

    @Override
    protected void generateResult() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        new Thread(() -> {
            queue.offer(FibonacciUtils.getFibonacci(16));
        }).start();

        try {
            result = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
