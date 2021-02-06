package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class LockSupportSolution extends AbstractSolution {

    public static void main(String[] args) {
        new LockSupportSolution().solute();
    }

    @Override
    protected void generateResult() {
        Thread main = Thread.currentThread();
        new Thread(() -> {
            result = FibonacciUtils.getFibonacci(16);
            LockSupport.unpark(main);
        }).start();

        LockSupport.park();
    }
}
