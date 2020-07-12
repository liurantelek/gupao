package com.lr.framework.aop;

public interface LRAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
