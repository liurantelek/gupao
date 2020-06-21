package com.lr.mvcFrameWork.v1.service.serviceImpl;

import com.lr.mvcFrameWork.v1.annotion.LrService;
import com.lr.mvcFrameWork.v1.service.ServiceDemo;

/**
 * @author liuran
 * @create 2020-06-21-10:38
 */
@LrService
public class ServiceDemoImpl implements ServiceDemo {
    public String testDemo() {
        return "hello";
    }
}
