package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

/**
 * WaitNotify实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class WaitNotifySolution extends AbstractSolution {

    public static void main(String[] args) {
        new WaitNotifySolution().solute();
    }

    @Override
    protected void generateResult() {
        new Thread(this::setResult).start();
        getResult();
    }

    private synchronized void setResult() {
        result = FibonacciUtils.getFibonacci(16);
        notifyAll();
    }

    private synchronized int getResult() {
        while (result == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
