package com.lr.framework.test;

import com.lr.framework.context.LRApplicationContext;
import com.lr.framework.controller.TestController;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: Test
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 18:13
 */
public class Test {

    public static void main(String[] args) {
//        LRApplicationContext applicationContext = new LRApplicationContext("classpath:application.properties");
//
//        try {
//            Object bean = applicationContext.getBean(TestController.class);
//            System.out.println(bean);
//            TestController controller = (TestController) bean;
//            controller.test();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        long t1 = System.currentTimeMillis();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-t1);
    }
}
