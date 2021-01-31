package org.nami.annotation;

import java.lang.annotation.*;

/**
 * EnableLoadBalance
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableLoadBalance {

    String value() default "";
}
