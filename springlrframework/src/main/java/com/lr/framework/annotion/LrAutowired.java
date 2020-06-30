package com.lr.framework.annotion;

import java.lang.annotation.*;

/**
 * @author liuran
 * @create 2020-06-20-23:18
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LrAutowired {
    String value() default "";
}
