package com.lr.framework.beans.config;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: LrBeanPostProcessor
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 19:11
 */
public class LrBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean,String beanName){
        return bean;
    }
    public Object postProcessAfterInitialization(Object bean,String beanName){
        return bean;
    }


}
