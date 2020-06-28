package com.lr.framework.beans.config;


import lombok.Data;

@Data
public class LRBeanDefinition {
    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;
}
