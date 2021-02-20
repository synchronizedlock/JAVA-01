package org.easley.spring.proxy.jdk;

import org.easley.spring.beans.Girl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Girl的JDK代理工厂
 *
 * @author Easley
 * @date 2021/2/19
 * @since 1.0
 */
public final class JdkGirlProxyFactory {

    private JdkGirlProxyFactory() {
        throw new IllegalStateException("Error creating instance of utility class.");
    }

    /**
     * 获取Girl的JDK代理
     *
     * @param girl girl实例
     * @return 代理实例
     * @author Easley
     * @date 2021/2/19
     * @since 1.0
     */
    public static Girl getGirlProxy(Girl girl) {
        JdkGirlProxyHandler handler = new JdkGirlProxyHandler(girl);
        ClassLoader cl = girl.getClass().getClassLoader();
        Class[] interfaces = girl.getClass().getInterfaces();

        return (Girl) Proxy.newProxyInstance(cl, interfaces, handler);
    }

    /**
     * JDK动态代理Handler实现
     *
     * @author Easley
     * @date 2021/2/20
     * @since 1.0
     */
    private static class JdkGirlProxyHandler implements InvocationHandler {

        /**
         * 被代理的girl
         */
        private final Object girl;

        public JdkGirlProxyHandler(Object girl) {
            this.girl = girl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("想知道关于妹子的事？我可以帮忙问");

            Object answer = method.invoke(girl, args);

            System.out.println("问完辣，就妹有搞不定的事嗷");
            System.out.println("--------------------------");

            return answer;
        }
    }
}
