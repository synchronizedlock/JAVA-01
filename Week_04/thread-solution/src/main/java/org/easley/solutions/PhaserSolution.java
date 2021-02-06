package org.easley.solutions;

import org.easley.utils.FibonacciUtils;

import java.util.concurrent.Phaser;

/**
 * Phaser实现
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class PhaserSolution extends AbstractSolution {

    public static void main(String[] args) {
        new PhaserSolution().solute();
    }

    @Override
    protected void generateResult() {
        Phaser phaser = new Phaser(1);
        phaser.register();
        new Thread(() -> {
            result = FibonacciUtils.getFibonacci(16);
            phaser.arriveAndAwaitAdvance();
        }).start();

        phaser.arriveAndAwaitAdvance();
    }
}
