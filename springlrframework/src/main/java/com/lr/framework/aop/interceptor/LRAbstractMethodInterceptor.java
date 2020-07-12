package com.lr.framework.aop.interceptor;

import com.lr.framework.aop.aspect.LRJointPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class LRAbstractMethodInterceptor implements LRMethodInterceptor {

    private Method aspectMethod;

    private Object aspectTarget;


    public LRAbstractMethodInterceptor(Method aspectMethod,Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public Object invoke(LrReflectiveMethodInvocation invocation) throws Throwable {
        return null;
    }


    protected Object invokeAdviceMethod(LRJointPoint jointPoint, Object result, Throwable tx) throws InvocationTargetException, IllegalAccessException {

        Class<?>[] parameterTypes = this.aspectMethod.getParameterTypes();

        if(null == parameterTypes || parameterTypes.length==0){
            this.aspectMethod.invoke(aspectTarget);
        }else {
            Object[] args = new Object[parameterTypes.length];
            for(int i=0;i<parameterTypes.length ;i++){
                if(parameterTypes[i] == LRJointPoint.class){
                    args[i] = jointPoint;
                }else if(parameterTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(parameterTypes[i] == Object.class){
                    args[i] = result;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }

        return result;
    }
}
