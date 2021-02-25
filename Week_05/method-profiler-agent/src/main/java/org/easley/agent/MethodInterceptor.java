package org.easley.agent;


import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 方法拦截器，拦截方法调用做AOP计时
 *
 * @author Easley
 * @date 2021/2/25
 * @since 1.0
 */
public class MethodInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            System.out.println(">>> " + method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
