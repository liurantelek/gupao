package com.lr.framework.aop.aspect;

import com.lr.framework.aop.interceptor.LRAbstractMethodInterceptor;
import com.lr.framework.aop.interceptor.LRMethodInterceptor;
import com.lr.framework.aop.interceptor.LrReflectiveMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class LRMethodAfterAdviceInterceport extends LRAbstractMethodInterceptor implements LRAdvice{

    private LRJointPoint jointPoint;

    public LRMethodAfterAdviceInterceport(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(LrReflectiveMethodInvocation invocation) throws Throwable {
        this.jointPoint = invocation;
        Object result = invocation.proceed();
        this.after( result,invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        return result;
    }

    private void after(Object result, Method method, Object[] arguments, Object aThis) throws InvocationTargetException, IllegalAccessException {
        super.invokeAdviceMethod(this.jointPoint,result,null);
    }


}
