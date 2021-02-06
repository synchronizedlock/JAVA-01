package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

/**
 * 使用内嵌标志位实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class BuiltInFlagSolution extends AbstractSolution {

    private boolean calculateFinished = false;

    public static void main(String[] args) {
        new BuiltInFlagSolution().solute();
    }

    @Override
    protected void generateResult() {
        new Thread(() -> {
            result = FibonacciUtils.getFibonacci(16);
            calculateFinished = true;
        }).start();

        while (!calculateFinished) {}
    }
}
