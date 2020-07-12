package com.lr.framework.context;

import com.lr.framework.annotion.LrAutowired;
import com.lr.framework.annotion.LrController;
import com.lr.framework.annotion.LrService;
import com.lr.framework.aop.LRAopProxy;
import com.lr.framework.aop.LrCglibAopProxy;
import com.lr.framework.aop.LrJDKDynamicAopProxy;
import com.lr.framework.aop.config.LRAopConfig;
import com.lr.framework.aop.support.LrAdvicedSupport;
import com.lr.framework.beans.LRBeanFactory;
import com.lr.framework.beans.LRBeanWrapper;
import com.lr.framework.beans.config.LRBeanDefinition;
import com.lr.framework.beans.config.LrBeanPostProcessor;
import com.lr.framework.beans.support.LRBeanDefinitionReader;
import com.lr.framework.beans.support.LRDefaultListableBeanFactory;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class LRApplicationContext extends LRDefaultListableBeanFactory implements LRBeanFactory {

    private String[] configLocations ;

    //单例的ioc容器
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    //通用的ioc的容器
    private Map<String,LRBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, LRBeanWrapper>();

    LRBeanDefinitionReader reader;

    public LRApplicationContext(String... configLocation){
        this.configLocations = configLocation;
        refresh();
    }

    @SneakyThrows
    @Override
    public void refresh() {
        //1、定位
        reader= new LRBeanDefinitionReader(this.configLocations);
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

    private void doRegisterBeanDefinition(List<LRBeanDefinition> beanDefinitionList) throws Exception {
        for(LRBeanDefinition beanDefinition : beanDefinitionList){
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    public Object getBean(Class<?> clazz) throws Exception{
        return getBean(clazz.getName());
    }

    public Object getBean(String beanName) throws Exception{
        LRBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;
        LrBeanPostProcessor processor = new LrBeanPostProcessor();
        processor.postProcessBeforeInitialization(instance,beanName);
        //初始化
         instance = instantiateBean(beanName,beanDefinition);

         //创建一个代理对象，使用cglib的还是jdk的
        LRBeanWrapper beanWrapper = new LRBeanWrapper(instance);
        this.factoryBeanInstanceCache.put(beanName,beanWrapper);

        processor.postProcessAfterInitialization(instance,beanName);

        //注入
        populateBean(beanName,beanDefinition,beanWrapper);
        
        return this.factoryBeanInstanceCache.get(beanName).getWrapperInstance();
    }

    private void populateBean(String beanName, LRBeanDefinition lrBeanDefinition, LRBeanWrapper lrBeanWrapper) {
        Object wrapperInstance = lrBeanWrapper.getWrapperInstance();
        //判断只有加了注解了的类才执行依赖注入
         Class<?> instanceClass = lrBeanWrapper.getWrapperClass();
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
                    autowiredName = reader.toLowerFirstCase(field.getType().getSimpleName());
                }
                field.setAccessible(true);
                try {
                    if(this.factoryBeanInstanceCache.get(autowiredName) == null){
                        try {
                            getBean(autowiredName);
                        } catch (Exception e) {

                        }
                    }
                    field.set(wrapperInstance,this.factoryBeanInstanceCache.get(autowiredName).getWrapperInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object instantiateBean(String beanName, LRBeanDefinition lrBeanDefinition) {
        Object instance = null;
        try {
            //默认是单例模式
            String beanClassName = lrBeanDefinition.getBeanClassName();
            if(this.singletonObjects.containsKey(beanClassName)){
                instance = singletonObjects.get(beanClassName);
            }else {
                Class<?> clazz = Class.forName(beanClassName);
                instance = clazz.newInstance();
                LrAdvicedSupport config = instantionAopConfig(lrBeanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);
                //如果符合创建一个代理对象
               if(config.pointCutMatch()){
                   instance = createProxy(config).getProxy();
               }
                    if(clazz.isAnnotationPresent(LrController.class)||
                    clazz.isAnnotationPresent(LrService.class)){
                        this.singletonObjects.put(beanClassName,instance);
                        this.singletonObjects.put(lrBeanDefinition.getFactoryBeanName(),instance);
                    }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    private LRAopProxy createProxy(LrAdvicedSupport config) {
        Class<?> targetClass = config.getTargetClass();
        //如果有接口创建jdk的动态代理，否则创界cglib的
        if(targetClass.getInterfaces().length>0){
            return new LrJDKDynamicAopProxy(config);
        }else {
            return new LrCglibAopProxy(config);
        }
    }

    /**
     *
     * @param lrBeanDefinition
     * @return
     */
    private LrAdvicedSupport instantionAopConfig(LRBeanDefinition lrBeanDefinition) {
        LRAopConfig config = new LRAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setApsectClass(this.reader.getConfig().getProperty("apsectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new LrAdvicedSupport(config);
    }

    public String[] getBeanDefinitionNames(){
        return beanDefinitionMap.keySet().toArray(new String[beanDefinitionMap.size()]);
    }
    public int getBeanDefinitionCount(){
        return beanDefinitionMap.keySet().size();
    }

    public Properties getConfig(){
       return this.reader.getConfig();
    }
}
