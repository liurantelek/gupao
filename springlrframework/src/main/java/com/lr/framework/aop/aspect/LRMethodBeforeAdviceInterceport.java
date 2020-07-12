package com.lr.framework.aop.aspect;

import com.lr.framework.aop.interceptor.LRAbstractMethodInterceptor;
import com.lr.framework.aop.interceptor.LrReflectiveMethodInvocation;

import java.lang.reflect.Method;

/**
 *
 */
public class LRMethodBeforeAdviceInterceport extends LRAbstractMethodInterceptor implements LRAdvice{

    private LRJointPoint jointPoint;

    public LRMethodBeforeAdviceInterceport(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private  void before(Method method,Object[] args,Object target)throws Throwable{
       super.invokeAdviceMethod(this.jointPoint,null,null);
    }

    @Override
    public Object invoke(LrReflectiveMethodInvocation invocation) throws Throwable {
        this.jointPoint  = invocation;
        this.before(invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        return invocation.proceed();
    }
}
