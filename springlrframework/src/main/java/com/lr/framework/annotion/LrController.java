package com.lr.framework.annotion;

import java.lang.annotation.*;

/**
 * @author liuran
 * @create 2020-06-20-22:55
 */
@Target ({ElementType.TYPE})
@Retention (RetentionPolicy.RUNTIME)
@Documented
public @interface LrController{
    String value()default "";
}
