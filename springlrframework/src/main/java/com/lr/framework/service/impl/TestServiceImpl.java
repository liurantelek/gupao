package com.lr.framework.service.impl;

import com.lr.framework.annotion.LrService;
import com.lr.framework.service.TestService;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: TestServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 19:05
 */
@LrService("testService")
public class TestServiceImpl implements TestService {
    public String testService(String name, String addr) {
        System.out.println("serviceImpl");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name+addr;
    }
}
