package com.lr.framework.webmvc.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: LrHandlerMapping
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 20:26
 */
@Data
public class LrHandlerMapping {



    public LrHandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    private Object controller;

    private Method method;

    private Pattern pattern; //url的正则匹配
}
