package com.lr.framework.beans;

import lombok.Data;

public class LRBeanWrapper {

    private Object wrapperInstance;

    private Class<?> wrapperClass;

    public LRBeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }

    public Object getWrapperInstance(){
        return  wrapperInstance;
    }

    public Class<?> getWrapperClass(){
        return this.wrapperInstance.getClass();
    }
}
