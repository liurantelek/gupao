package com.lr.framework.beans.support;

import com.lr.framework.beans.config.LRBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LRBeanDefinitionReader  {

    private Properties config = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private final String SCAN_PACKAGE = "scanPackage";

    public LRBeanDefinitionReader(String... locations) {
    }

    public List<LRBeanDefinition> loadBeanDefinitions(String... locations){
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
        return null;
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File files = new File(url.getPath());
        for(File file : files.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else {
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                String className = (scanPackage+"."+file.getName().replace(".class",""));
                classNames.add(className);
            }
        }

    }

    public List<LRBeanDefinition> loadBeanDefinitions(){

        return null;
    }
}
