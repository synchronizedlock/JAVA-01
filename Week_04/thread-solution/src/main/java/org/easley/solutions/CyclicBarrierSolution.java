package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class CyclicBarrierSolution extends AbstractSolution {

    public static void main(String[] args) {
        new CyclicBarrierSolution().solute();
    }

    @Override
    protected void generateResult() {
        CyclicBarrier cb = new CyclicBarrier(2);
        new Thread(() -> {
            result = FibonacciUtils.getFibonacci(16);
            try {
                cb.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
