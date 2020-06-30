package com.lr.framework.annotion;

import javax.xml.bind.ValidationEvent;
import java.lang.annotation.*;

/**
 * @author liuran
 * @create 2020-06-20-23:50
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LrRequestParam {
    String value() default "";
}
