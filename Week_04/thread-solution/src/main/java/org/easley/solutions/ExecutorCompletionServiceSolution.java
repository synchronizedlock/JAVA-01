package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorCompletionService实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class ExecutorCompletionServiceSolution extends AbstractSolution {

    public static void main(String[] args) {
        new ExecutorCompletionServiceSolution().solute();
    }

    @Override
    protected void generateResult() {
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<Integer> completionPool = new ExecutorCompletionService<>(singlePool);

        completionPool.submit(() -> FibonacciUtils.getFibonacci(16));
        try {
            result = completionPool.take().get();
            singlePool.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
