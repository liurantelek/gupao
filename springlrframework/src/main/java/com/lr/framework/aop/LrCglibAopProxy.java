package com.lr.framework.aop;

import com.lr.framework.aop.interceptor.LrReflectiveMethodInvocation;
import com.lr.framework.aop.support.LrAdvicedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class LrCglibAopProxy implements LRAopProxy , MethodInterceptor {

    private LrAdvicedSupport advised;

    public LrCglibAopProxy(LrAdvicedSupport support) {
        this.advised = support;
    }

    public Object getProxy() {
        return this.advised.getTargetClass().getClassLoader();
    }

    public Object getProxy(ClassLoader classLoader) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTargetClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        List<Object> interceptorAndDynamicMatchers = this.advised.getInterceptorAndDynamicInterceptorAdvice(method,this.advised.getTargetClass());
        LrReflectiveMethodInvocation invocation = new LrReflectiveMethodInvocation(proxy,this.advised.getTarget(),method,
                args,this.advised.getTargetClass(),interceptorAndDynamicMatchers);
        return invocation.proceed();
    }
}
