package com.lr.framework.controller;

import com.lr.framework.annotion.LrController;
import com.lr.framework.annotion.LrRequestMapping;
import com.lr.framework.annotion.LrRequestParam;
import com.lr.framework.webmvc.servlet.LrModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: MyAction
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/6 19:03
 */
@LrController
@LrRequestMapping("/myaction")
public class MyAction {

    @LrRequestMapping("/query.json")
    public LrModelAndView query(HttpServletRequest request, HttpServletResponse response,@LrRequestParam("name") String name,@LrRequestParam("addr") String addr){
        return null;
    }
}