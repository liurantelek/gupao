package com.lr.framework.aop.interceptor;

import com.lr.framework.aop.aspect.LRJointPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 */
public class LrReflectiveMethodInvocation implements LRJointPoint {

    private Object proxy;

    private  Method method;

    private Object target;

    private Class<?> targetClass;

    private Object[] arguments;

    List<Object> interceptorAndDynamicMatchers;

    private int currentInterceptorIndex = -1;


    /**
     *
     * @param proxy 代理对象
     * @param target 目标对象
     * @param method 方法
     * @param arguments 参数
     * @param targetClass 目标对象class
     * @param interceptorAndDynamicMatchers 拦截器链
     */
    public LrReflectiveMethodInvocation(
            Object proxy, Object target, Method method,Object[] arguments,
             Class<?> targetClass, List<Object> interceptorAndDynamicMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
        this.interceptorAndDynamicMatchers = interceptorAndDynamicMatchers;
        //定义一个索引，从-1开始记录拦截器的位置
    }

    public Object proceed() throws Throwable {
        if(this.currentInterceptorIndex == this.interceptorAndDynamicMatchers.size()-1){
            return this.method.invoke(this.target,this.arguments);
        }
        Object interceptorOrInterceptionAdvice = this.interceptorAndDynamicMatchers.get(++this.currentInterceptorIndex);
        if(interceptorOrInterceptionAdvice instanceof LRMethodInterceptor){
            LRMethodInterceptor interceptor = (LRMethodInterceptor) interceptorOrInterceptionAdvice;
            return interceptor.invoke(this);
        }else {
            return proceed();
        }

    }

    public Object getThis() {
        return this.target;
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setUserAttribute(String key, Object value) {
        if(value!=null){
            if(userAttribute.get(key) ==null){
                userAttribute.put(key,value);
            }
        }else {
            if(userAttribute!=null){
                userAttribute.remove(key);
            }
        }
    }

    public Object getUerAttribute(String key) {
        return userAttribute!=null?userAttribute.get(key):null;
    }
}
