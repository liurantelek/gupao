package com.lr.mvcFrameWork.v1.controller;

import com.lr.mvcFrameWork.v1.annotion.LrAutowired;
import com.lr.mvcFrameWork.v1.annotion.LrController;
import com.lr.mvcFrameWork.v1.annotion.LrRequestMapping;
import com.lr.mvcFrameWork.v1.annotion.LrRequestParam;
import com.lr.mvcFrameWork.v1.service.ServiceDemo;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liuran
 * @create 2020-06-20-23:35
 */
@LrController()
@LrRequestMapping ("/demo")
public class ControllerDemo {

    @LrAutowired ()
    private ServiceDemo serviceDemo;

    @LrRequestMapping ("/query")
    public String hellow(HttpServletRequest req, HttpServletResponse resp,
                         @LrRequestParam("dd") String ss){
        String result =  serviceDemo.testDemo();
        System.out.println (ss);
        System.out.println (result);
        return result;
    }
}
