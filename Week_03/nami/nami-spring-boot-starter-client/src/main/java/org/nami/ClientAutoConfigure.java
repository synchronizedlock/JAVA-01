package org.nami;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ClientAutoConfigure
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Configuration
@Import(value = {org.nami.AutoRegisterListener.class})
@EnableConfigurationProperties(value = {ClientConfigProperties.class})
public class ClientAutoConfigure {
}
