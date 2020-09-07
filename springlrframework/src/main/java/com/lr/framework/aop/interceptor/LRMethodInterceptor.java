package com.lr.framework.aop.interceptor;

/**
 * 拦截器链的接口
 */
public interface LRMethodInterceptor {



    Object invoke(LrReflectiveMethodInvocation invocation) throws Throwable;
}
