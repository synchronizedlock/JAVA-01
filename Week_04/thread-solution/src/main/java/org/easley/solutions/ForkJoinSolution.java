package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * ForkJoin实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class ForkJoinSolution extends AbstractSolution {

    public static void main(String[] args) {
        new ForkJoinSolution().solute();
    }

    @Override
    protected void generateResult() {
        ForkJoinPool pool = new ForkJoinPool(1);
        ForkJoinTask<Integer> task = pool.submit(() -> FibonacciUtils.getFibonacci(16));

        try {
            result = task.get();
            pool.shutdownNow();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
