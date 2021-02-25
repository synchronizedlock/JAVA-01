package org.easley.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * 方法性能分析器（其实只是做个耗时计算）
 *
 * @author Easley
 * @date 2021/2/25
 * @since 1.0
 */
public class MethodProfilerAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println(">>> Method profiler agent start.");
        System.out.println(">>> ----------------------------");

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder
                    , TypeDescription typeDescription
                    , ClassLoader classLoader) {

                // 拦截被增强类的所有方法调用
                return builder.method(ElementMatchers.<MethodDescription>any())
                        // 对方法增加耗时计算
                        .intercept(MethodDelegation.to(MethodInterceptor.class));
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, DynamicType dynamicType) { }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) { }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, Throwable throwable) { }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule) { }
        };

        new AgentBuilder.Default()
                .type(ElementMatchers.<TypeDescription>nameStartsWith("org.easley.demo"))
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }
}
