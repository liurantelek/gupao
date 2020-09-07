package com.lr.framework.aspect;

import com.lr.framework.aop.aspect.LRJointPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class LogAspect {

    public void before(LRJointPoint jointPoint){
        long startTime = System.currentTimeMillis();
       jointPoint.setUserAttribute("startTime_"+jointPoint.getMethod().getName(), startTime);
       log.info("Invoker Before Method!!"+"TargetObjectd"+jointPoint.getThis()
       +"args"+ Arrays.toString(jointPoint.getArguments()));
        System.out.println("Invoker Before Method!!"+"TargetObjectd="+jointPoint.getThis()
                +"args"+ Arrays.toString(jointPoint.getArguments()));
        System.out.println("startTime1:"+startTime);
    }

    public void after(LRJointPoint jointPoint){
        log.info("Invoker After Method!!"+"TargetObjectd"+jointPoint.getThis()
                +"args"+ Arrays.toString(jointPoint.getArguments()));
        System.out.println("Invoker After Method!!"+"TargetObjectd"+jointPoint.getThis()
                +"args"+ Arrays.toString(jointPoint.getArguments()));
        Object time = jointPoint.getUerAttribute("startTime_"+jointPoint.getMethod().getName());
        long startTime = Long.parseLong(time.toString());
        System.out.println("startTime2:"+startTime);
        log.info("userTime"+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println("userTime"+(System.currentTimeMillis()-startTime)+"ms");
    }

    public void afterThrowing(LRJointPoint jointPoint){
        log.info("Invoker After Method!!"+"TargetObjectd"+jointPoint.getThis()
                +"args"+ Arrays.toString(jointPoint.getArguments()));
        System.out.println("Invoker After Method!!"+"TargetObjectd"+jointPoint.getThis()
                +"args"+ Arrays.toString(jointPoint.getArguments()));
    }

}
