package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * AwaitTermination实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class AwaitTerminationSolution extends AbstractSolution {

    public static void main(String[] args) {
        new AwaitTerminationSolution().solute();
    }

    @Override
    protected void generateResult() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> result = FibonacciUtils.getFibonacci(16));
        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
