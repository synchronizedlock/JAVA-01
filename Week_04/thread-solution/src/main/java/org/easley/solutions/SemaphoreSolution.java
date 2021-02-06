package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.Semaphore;

/**
 * Semaphore实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class SemaphoreSolution extends AbstractSolution {

    public static void main(String[] args) {
        new SemaphoreSolution().solute();
    }

    @Override
    protected void generateResult() {
        Semaphore semaphore = new Semaphore(0);
        new Thread(() -> {
            result = FibonacciUtils.getFibonacci(16);
            semaphore.release();
        }).start();

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
