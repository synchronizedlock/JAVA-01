package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class ReentrantLockSolution extends AbstractSolution {

    final Lock lock = new ReentrantLock();
    final Condition calculateFinished = lock.newCondition();

    public static void main(String[] args) {
        new ReentrantLockSolution().solute();
    }

    @Override
    protected void generateResult() {
        new Thread(this::setResult).start();
        getResult();
    }

    private void setResult() {
        try {
            lock.lock();
            result = FibonacciUtils.getFibonacci(16);
            calculateFinished.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private int getResult() {
        try {
            lock.lock();
            while (result == 0) {
                try {
                    calculateFinished.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        } finally {
            lock.unlock();
        }
    }
}
