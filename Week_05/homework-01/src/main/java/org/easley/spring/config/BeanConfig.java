package org.easley.spring.config;

import org.easley.spring.beans.Girl;
import org.easley.spring.beans.MoeGirl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean的管理配置
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
@Configuration
public class BeanConfig {

    @Bean
    public Girl haruka() {
        MoeGirl haruka = new MoeGirl();
        haruka.setName("Haruka");
        haruka.setWeChat("haruka1996");
        return haruka;
    }
}
