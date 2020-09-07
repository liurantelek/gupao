package com.lr.framework.aop;

import com.lr.framework.aop.interceptor.LrReflectiveMethodInvocation;
import com.lr.framework.aop.support.LrAdvicedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class LrJDKDynamicAopProxy implements LRAopProxy , InvocationHandler {

    private LrAdvicedSupport advised;

    public LrJDKDynamicAopProxy(LrAdvicedSupport support) {
        this.advised = support;
    }

    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object>  interceptorAndDynamicMatchers = this.advised.getInterceptorAndDynamicInterceptorAdvice(method,this.advised.getTargetClass());
        LrReflectiveMethodInvocation invocation = new LrReflectiveMethodInvocation(proxy,this.advised.getTarget(),method,
                args,this.advised.getTargetClass(),interceptorAndDynamicMatchers);
        return invocation.proceed();
    }
}
