package com.lr.framework.aop.aspect;

import com.lr.framework.aop.interceptor.LRAbstractMethodInterceptor;
import com.lr.framework.aop.interceptor.LRMethodInterceptor;
import com.lr.framework.aop.interceptor.LrReflectiveMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class LRMethodAfterReturningAdviceInterceport extends LRAbstractMethodInterceptor implements LRAdvice{

    private LRJointPoint jointPoint;

    public LRMethodAfterReturningAdviceInterceport(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(LrReflectiveMethodInvocation invocation) throws Throwable {
        Object returnValue = invocation.proceed();
        jointPoint = invocation;
        this.aferReturning(returnValue,invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        return returnValue;
    }

    private void aferReturning(Object returnValue, Method method, Object[] arguments, Object aThis) throws InvocationTargetException, IllegalAccessException {
        super.invokeAdviceMethod(jointPoint,returnValue,null);
    }
}
