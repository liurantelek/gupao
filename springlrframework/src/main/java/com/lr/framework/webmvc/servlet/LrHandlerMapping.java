package com.lr.framework.webmvc.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: LrHandlerMapping
 * @Description:
 * @Author: 刘然
 * @Date: 2020/6/30 20:26
 */
@Data
public class LrHandlerMapping {



    public LrHandlerMapping(Pattern pattern,Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    //保存方法的对应实例
    private Object controller;

    //保存映射的方法
    private Method method;

    //url的正则匹配
    private Pattern pattern;
}
