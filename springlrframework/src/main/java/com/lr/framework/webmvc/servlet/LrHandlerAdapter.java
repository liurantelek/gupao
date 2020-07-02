package com.lr.framework.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: LrHandlerAdapter
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/1 20:34
 */
public class LrHandlerAdapter {

    boolean support(Object handler){
        return  handler instanceof LrHandlerMapping;
    };

    public LrModelAndView handle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        return null;
    }
}
