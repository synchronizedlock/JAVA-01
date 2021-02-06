package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class CompletableFutureSolution extends AbstractSolution {

    public static void main(String[] args) {
        new CompletableFutureSolution().solute();
    }

    @Override
    protected void generateResult() {
        CompletableFuture.supplyAsync(() -> FibonacciUtils.getFibonacci(16)).thenAccept(fib -> result = fib);
    }
}
