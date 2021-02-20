package org.easley.spring.config;

import org.easley.spring.beans.MoeGirl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 欢迎每个妹子入场
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
@Component
public class IdolPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MoeGirl) {
            System.out.println("有请少女偶像：" + ((MoeGirl) bean).getName() + "!");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
