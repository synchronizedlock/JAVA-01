package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.Exchanger;

/**
 * Exchanger实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class ExchangerSolution extends AbstractSolution {

    public static void main(String[] args) {
        new ExchangerSolution().solute();
    }

    @Override
    protected void generateResult() {
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            try {
                exchanger.exchange(FibonacciUtils.getFibonacci(16));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            result = exchanger.exchange(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
