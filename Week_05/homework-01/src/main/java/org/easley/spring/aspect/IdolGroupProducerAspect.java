package org.easley.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 在经纪人做自我介绍前后，主持人对发言开始和结束要引入和承接
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
@Component
@Aspect
public class IdolGroupProducerAspect {

    @Pointcut(value = "execution(* org.easley.spring.beans.IdolGroupProducer.selfIntroduction())")
    public void point() {}

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(">>> 下面有请团队经纪人。");
        joinPoint.proceed();
        System.out.println(">>> 好的，这里都是来看妹子的，宁可以爬了。");
    }
}
