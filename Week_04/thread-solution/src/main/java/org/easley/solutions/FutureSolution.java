package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Future实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class FutureSolution extends AbstractSolution {

    public static void main(String[] args) {
        new FutureSolution().solute();
    }

    @Override
    protected void generateResult() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Future<Integer> task = pool.submit(() -> FibonacciUtils.getFibonacci(16));
        try {
            result = task.get();
            pool.shutdownNow();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
