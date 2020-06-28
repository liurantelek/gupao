package com.lr.framework.context;

import com.lr.framework.beans.LRBeanFactory;
import com.lr.framework.beans.config.LRBeanDefinition;
import com.lr.framework.beans.support.LRBeanDefinitionReader;
import com.lr.framework.beans.support.LRDefaultListableBeanFactory;

import java.util.List;

public class LRApplicationContext extends LRDefaultListableBeanFactory implements LRBeanFactory {

    private String[] configLocations ;

    public LRApplicationContext(String... configLocation){
        this.configLocations = configLocation;
    }

    @Override
    public void refresh() {
        //1、定位
        LRBeanDefinitionReader reader = new LRBeanDefinitionReader(this.configLocations);
        //2、加载配置文件，扫描相关的类，把他们封装成beanDefinition
        List<LRBeanDefinition> beanDefinitionList = reader.loadBeanDefinitions();
        //3、注册，把配置信息放到容器里面
        doRegisterBeanDefinition(beanDefinitionList);
        //4、把不是延时加载的类提前初始化
        doAutowired();
        
    }

    private void doAutowired() {
    }

    private void doRegisterBeanDefinition(List<LRBeanDefinition> beanDefinitionList) {
    }

    public Object getBean(String beanName) {
        return null;
    }
}
