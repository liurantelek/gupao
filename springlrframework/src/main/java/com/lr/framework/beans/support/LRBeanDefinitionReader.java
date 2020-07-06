package com.lr.framework.beans.support;

import com.lr.framework.annotion.LrController;
import com.lr.framework.annotion.LrService;
import com.lr.framework.beans.config.LRBeanDefinition;

import java.beans.BeanDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LRBeanDefinitionReader  {

    private Properties config = new Properties();

    private List<String> registerBeanClasses = new ArrayList<String>();

    private final String SCAN_PACKAGE = "scanPackage";

    public LRBeanDefinitionReader(String... locations) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }


    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.","/"));
        File files = new File(url.getPath());
        for(File file : files.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else {
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                String className = (scanPackage+"."+file.getName().replace(".class",""));
                registerBeanClasses.add(className);
            }
        }

    }

    public List<LRBeanDefinition> loadBeanDefinitions(){
        List<LRBeanDefinition> beanDefinitions = new ArrayList<LRBeanDefinition>();
        for(String  className : registerBeanClasses){
            LRBeanDefinition beanDefinition = doCreateBeanDefinition(className);
            if(beanDefinition == null){
                continue;
            }
            beanDefinitions.add(beanDefinition);
        }
        return beanDefinitions;
    }

    private LRBeanDefinition doCreateBeanDefinition(String className){
        try {

            Class<?> beanClass = Class.forName(className);
            //有可能是一个借口，用它的实现类作为beanClassName
            if(!beanClass.isInterface()){
                if(beanClass.isAnnotationPresent(LrController.class)||
                beanClass.isAnnotationPresent(LrService.class)){
                    LRBeanDefinition beanDefinition = new LRBeanDefinition();
                    beanDefinition.setBeanClassName(className);
                    beanDefinition.setFactoryBeanName(toLowerFirstCase(beanClass.getSimpleName()));
                    return beanDefinition;
                }

            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  String toLowerFirstCase(String simpleName){
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public Properties getConfig(){
        return config;
    }
}
