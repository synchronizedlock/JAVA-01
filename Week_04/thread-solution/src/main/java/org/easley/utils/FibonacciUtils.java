package org.easley.utils;

/**
 * 计算斐波拉契数列工具类
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public class FibonacciUtils {

    public static int getFibonacci(int n) {
        return fibonacci(n);
    }

    private static int fibonacci(int a) {
        if (a < 2) {
            return 1;
        }
        return fibonacci(a - 1) + fibonacci(a - 2);
    }
}
