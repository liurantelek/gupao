package com.lr.framework.controller;

import com.lr.framework.annotion.LrAutowired;
import com.lr.framework.annotion.LrController;
import com.lr.framework.annotion.LrRequestMapping;
import com.lr.framework.service.TestService;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: TestController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 9:57
 */
@LrController
@LrRequestMapping("/")
public class TestController {

    @LrAutowired
    private TestService testService;

    @LrRequestMapping("/test")
    public String test(){
        System.out.println(testService); ;
        return null;
    }
}
