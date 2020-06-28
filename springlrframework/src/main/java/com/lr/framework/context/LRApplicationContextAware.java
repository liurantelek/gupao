package com.lr.framework.context;

/**
 * 通过解耦的方式获得ioc容器的顶层设计
 * 后边通过监听器去扫描所有的类，只要实现了此接口，
 * 将自动调用setApplication方法从而将ioc容器注册到目标类中
 */
public interface LRApplicationContextAware {
     void setApplicationContext(LRApplicationContext applicationContext);
}
