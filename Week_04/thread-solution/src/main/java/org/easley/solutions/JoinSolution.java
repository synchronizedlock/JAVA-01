package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

/**
 * Join实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class JoinSolution extends AbstractSolution {

    public static void main(String[] args) {
        new JoinSolution().solute();
    }

    @Override
    protected void generateResult() {
        Thread t = new Thread(() -> {
            result = FibonacciUtils.getFibonacci(16);
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
