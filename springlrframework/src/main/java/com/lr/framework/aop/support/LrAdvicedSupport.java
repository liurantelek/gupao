package com.lr.framework.aop.support;

import com.lr.framework.aop.aspect.LRMethodAfterAdviceInterceport;
import com.lr.framework.aop.aspect.LRMethodAfterThrowingAdviceInterceport;
import com.lr.framework.aop.aspect.LRMethodBeforeAdviceInterceport;
import com.lr.framework.aop.config.LRAopConfig;
import com.lr.framework.aop.interceptor.LRMethodInterceptor;
import com.lr.framework.aop.interceptor.LrReflectiveMethodInvocation;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrAdvicedSupport {

    private Class<?> targetClass;

    private Object target;

    private LRAopConfig config;

    private Pattern pointCutClassPattern;

    Map<Method,List<Object>> methodCache ;

    public LrAdvicedSupport(LRAopConfig config) {
        this.config = config;
    }


    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;

        try {
            parse();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析正则
     */
    private void parse() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String pointCut = config.getPointCut().
                replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "(")
                .replaceAll("\\)", ")");
        String pointCutClassRegex = pointCut.substring(0, pointCut.lastIndexOf("(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutClassRegex.substring(
                pointCutClassRegex.lastIndexOf(" ") + 1));

        methodCache = new HashMap<Method, List<Object>>();
        Class<?> aspectClass = Class.forName(this.config.getApsectClass());
        Map<String,Method> aspectMethods = new HashMap<String, Method>();
        for(Method method : aspectClass.getDeclaredMethods()){
            aspectMethods.put(method.getName(),method);
        }
        Object aspectInstace = aspectClass.newInstance();
        Pattern compile = Pattern.compile(pointCut);
        Method[] methods = this.targetClass.getMethods();

        for(Method m :methods){
            String methodString = m.toString();
            if(methodString.contains("throws")){
                methodString = methodString.substring(0,methodString.lastIndexOf("throws")).trim();
            }
            Matcher matcher = compile.matcher(methodString);

            List<Object> advices =new LinkedList<Object>();
            if(matcher.matches()){
                //如果是需要包装的，包装成一个methodInterceptor
                //before after afterThrowing

                if(!(null == config.getAspectBefore()||"".equals(config.getAspectBefore()))){
                    //创建一个advice对象
                    LRMethodInterceptor beforeInterceptor = new LRMethodBeforeAdviceInterceport(
                            aspectMethods.get(config.getAspectBefore()),aspectInstace);
                    advices.add(beforeInterceptor);
                }
                if(!(null == config.getAspectAfter()||"".equals(config.getAspectAfter()))){
                    //创建一个advice对象
                    LRMethodInterceptor afterInterceptor = new LRMethodAfterAdviceInterceport(
                            aspectMethods.get(config.getAspectAfter()),aspectInstace);
                    advices.add(afterInterceptor);
                }
                if(!(null == config.getAspectAfterThrow()||"".equals(config.getAspectAfterThrow()))){
                    //创建一个advice对象
                    LRMethodAfterThrowingAdviceInterceport afterThrowInterceptor = new LRMethodAfterThrowingAdviceInterceport(
                            aspectMethods.get(config.getAspectAfterThrow()),aspectInstace);
                  afterThrowInterceptor.setThrowName(config.getAspectAfterThrowingName());
                    advices.add(afterThrowInterceptor);
                }
            }
            methodCache.put(m,advices);
        }

    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<Object> getInterceptorAndDynamicInterceptorAdvice(Method method, Class<?> targetClass) throws NoSuchMethodException {
        List<Object> cache = methodCache.get(method);
        if(cache == null){
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cache = methodCache.get(m);
            methodCache.put(method,cache);
        }
        return cache;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
