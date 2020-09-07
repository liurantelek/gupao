package com.lr.framework.aop.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public interface LRJointPoint {

    Map<String,Object> userAttribute = new HashMap<String, Object>();

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value );

    Object getUerAttribute(String key);
}
