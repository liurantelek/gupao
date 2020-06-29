package com.lr.framework.context;

import com.lr.framework.beans.LRBeanFactory;
import com.lr.framework.beans.LRBeanWrapper;
import com.lr.framework.beans.config.LRBeanDefinition;
import com.lr.framework.beans.support.LRBeanDefinitionReader;
import com.lr.framework.beans.support.LRDefaultListableBeanFactory;

import java.beans.beancontext.BeanContext;
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
        for(String beanName : beanDefinitionMap.keySet()){
            LRBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(!beanDefinition.isLazyInit()){
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<LRBeanDefinition> beanDefinitionList) {
        for(LRBeanDefinition beanDefinition : beanDefinitionList){
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    public Object getBean(String beanName) {
        
        //初始化
        instantiateBean(beanName,new LRBeanDefinition(),new LRBeanWrapper());
        
        populateBean(beanName,new LRBeanDefinition(),new LRBeanWrapper());
        
        return null;
    }

    private void populateBean(String beanName, LRBeanDefinition lrBeanDefinition, LRBeanWrapper lrBeanWrapper) {
    }

    private void instantiateBean(String beanName, LRBeanDefinition lrBeanDefinition, LRBeanWrapper lrBeanWrapper) {
    }
}
