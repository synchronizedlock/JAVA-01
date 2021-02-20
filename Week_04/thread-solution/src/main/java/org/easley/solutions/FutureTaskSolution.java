package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class FutureTaskSolution extends AbstractSolution {

    public static void main(String[] args) {
        new FutureTaskSolution().solute();
    }

    @Override
    protected void generateResult() {
        FutureTask<Integer> task = new FutureTask<>(() -> FibonacciUtils.getFibonacci(16));
        new Thread(task).start();

        try {
            result = task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}