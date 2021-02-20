package org.easley.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 开场白
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
@Component
public class IdolGroupPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("少女偶像GamingGirls全员集结完毕！");
        System.out.println("----------------------------");
    }
}
