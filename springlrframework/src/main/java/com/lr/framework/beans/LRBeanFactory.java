package com.lr.framework.beans;

public interface LRBeanFactory {

    /**
     * 根据beanName从ioc容器中获得一个实例bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
