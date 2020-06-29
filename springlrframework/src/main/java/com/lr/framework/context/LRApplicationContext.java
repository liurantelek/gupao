package com.lr.framework.context;

import com.lr.framework.annotion.LrAutowired;
import com.lr.framework.annotion.LrController;
import com.lr.framework.annotion.LrService;
import com.lr.framework.beans.LRBeanFactory;
import com.lr.framework.beans.LRBeanWrapper;
import com.lr.framework.beans.config.LRBeanDefinition;
import com.lr.framework.beans.support.LRBeanDefinitionReader;
import com.lr.framework.beans.support.LRDefaultListableBeanFactory;
import com.sun.deploy.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRApplicationContext extends LRDefaultListableBeanFactory implements LRBeanFactory {

    private String[] configLocations ;

    //单例的ioc容器
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    //通用的ioc的容器
    private Map<String,LRBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, LRBeanWrapper>();

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
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doRegisterBeanDefinition(List<LRBeanDefinition> beanDefinitionList) {
        for(LRBeanDefinition beanDefinition : beanDefinitionList){
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    public Object getBean(Class<?> clazz) throws Exception{
        return getBean(clazz.getName());
    }

    public Object getBean(String beanName) throws Exception{
        
        //初始化
        LRBeanWrapper beanWrapper = instantiateBean(beanName,new LRBeanDefinition());
        this.factoryBeanInstanceCache.put(beanName,beanWrapper);
        //注入
        populateBean(beanName,new LRBeanDefinition(),beanWrapper);
        
        return null;
    }

    private void populateBean(String beanName, LRBeanDefinition lrBeanDefinition, LRBeanWrapper lrBeanWrapper) {
        Object wrapperInstance = lrBeanWrapper.getWrapperInstance();
        //判断只有加了注解了的类才执行依赖注入
        final Class<?> instanceClass = lrBeanWrapper.getWrapperClass();
        if(!(instanceClass.isAnnotationPresent(LrController.class) || instanceClass.isAnnotationPresent(LrService.class))){
            return;
        }
        //获得所有的fields
        Field[] fields = instanceClass.getDeclaredFields();
        for(Field field : fields){

            if(field.isAnnotationPresent(LrAutowired.class)){
                LrAutowired autowired = field.getAnnotation(LrAutowired.class);
                String autowiredName = autowired.value().trim();
                if("".equals(autowiredName)){
                    autowiredName = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(wrapperInstance,this.factoryBeanInstanceCache.get(autowiredName).getWrapperInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private LRBeanWrapper instantiateBean(String beanName, LRBeanDefinition lrBeanDefinition) {
        Object instance = null;
        try {
            //默认是单例模式
            String beanClassName = lrBeanDefinition.getBeanClassName();
            if(this.singletonObjects.containsKey(beanClassName)){
                instance = singletonObjects.get(beanClassName);
            }else {
                Class<?> clazz = Class.forName(beanClassName);
                instance = clazz.newInstance();
                this.singletonObjects.put(beanClassName,instance);
                this.singletonObjects.put(lrBeanDefinition.getFactoryBeanName(),instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LRBeanWrapper beanWrapper = new LRBeanWrapper(instance);
        return beanWrapper;
    }
}
