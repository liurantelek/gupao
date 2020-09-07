package com.lr.framework.aop.aspect;

import com.lr.framework.aop.interceptor.LRAbstractMethodInterceptor;
import com.lr.framework.aop.interceptor.LRMethodInterceptor;
import com.lr.framework.aop.interceptor.LrReflectiveMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class LRMethodAfterThrowingAdviceInterceport extends LRAbstractMethodInterceptor implements LRAdvice{

    private String throwName;

    public LRMethodAfterThrowingAdviceInterceport(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(LrReflectiveMethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            invokeAdviceMethod(invocation,null,e);
            throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwName = throwName;
    }
}
