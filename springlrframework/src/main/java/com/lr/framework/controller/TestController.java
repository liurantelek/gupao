package com.lr.framework.controller;

import com.lr.framework.annotion.LrAutowired;
import com.lr.framework.annotion.LrController;
import com.lr.framework.annotion.LrRequestMapping;
import com.lr.framework.annotion.LrRequestParam;
import com.lr.framework.service.TestService;
import com.lr.framework.webmvc.servlet.LrModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: TestController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 9:57
 */
@LrController
@LrRequestMapping("/testcontroller")
public class TestController {

    @LrAutowired
    private TestService testService;

    @LrRequestMapping("/test")
    public LrModelAndView test(HttpServletRequest request, HttpServletResponse response,
                               @LrRequestParam("name") String name, @LrRequestParam("addr") String addr){
        System.out.println(testService);
        String resutl = testService.testService(name,addr);

        Map<String,Object> rows = new HashMap<String,Object>();
        rows.put("detail",resutl);
        LrModelAndView modelAndView = new LrModelAndView("first",rows);
        return modelAndView;
    }
}
