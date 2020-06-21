package com.lr.mvcFrameWork.v1.annotion;

import java.lang.annotation.*;

/**
 * @author liuran
 * @create 2020-06-20-23:26
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LrRequestMapping {
    String value();
}
