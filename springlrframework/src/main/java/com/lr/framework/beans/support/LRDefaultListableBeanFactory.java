package com.lr.framework.beans.support;

import com.lr.framework.beans.config.LRBeanDefinition;
import com.lr.framework.context.LRAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRDefaultListableBeanFactory extends LRAbstractApplicationContext {

    /**
     * 存储注册信息的beanDefinition
     */
    private final Map<String, LRBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,LRBeanDefinition>(256);

    @Override
    public void refresh() {
        super.refresh();
    }
}
