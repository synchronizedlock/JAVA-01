package org.easley.spring.proxy.cglib;

import org.easley.spring.beans.Girl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Girl的Cglib代理工厂
 *
 * @author Easley
 * @date 2021/2/19
 * @since 1.0
 */
public final class CglibGirlProxyFactory {

    private CglibGirlProxyFactory() {
        throw new IllegalStateException("Error creating instance of utility class.");
    }

    /**
     * 获取Girl的Cglib代理
     *
     * @param girl girl实例
     * @return 代理实例
     * @author Easley
     * @date 2021/2/19
     * @since 1.0
     */
    public static Girl getGirlProxy(Girl girl) {
        return (Girl) Enhancer.create(Girl.class, new CglibGirlProxyHandler(girl));
    }

    /**
     * cglib动态代理Handler实现
     *
     * @author Easley
     * @date 2021/2/20
     * @since 1.0
     */
    private static class CglibGirlProxyHandler implements MethodInterceptor {

        /**
         * 被代理的girl
         */
        private final Girl girl;

        private CglibGirlProxyHandler(Girl girl) {
            this.girl = girl;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("想知道关于妹子的事？我可以帮忙问");

            Object answer = method.invoke(girl, args);

            System.out.println("问完辣，就妹有搞不定的事嗷");
            System.out.println("--------------------------");

            return answer;
        }
    }
}
