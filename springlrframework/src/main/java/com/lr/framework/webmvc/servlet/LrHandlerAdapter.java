package com.lr.framework.webmvc.servlet;

import com.lr.framework.annotion.LrRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: LrHandlerAdapter
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/1 20:34
 */
public class LrHandlerAdapter {

    public boolean support(Object handler){
        return  handler instanceof LrHandlerMapping;
    };

    public LrModelAndView handle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        LrHandlerMapping handlerMapping = (LrHandlerMapping) handler;

        //把方法的参数列表和request的参数列表按照顺序一一对应
        Map<String,Integer> paramIndexMapping = new HashMap<String, Integer>();
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
        for(int i = 0;i<pa.length;i++){
            for(Annotation a : pa[i]){
                if(a instanceof LrRequestParam){
                    String paramName = ((LrRequestParam) a).value();
                    if(!"".equals(paramName.trim())){
                        paramIndexMapping.put(paramName,i);
                    }
                }
            }
        }

        Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
        for(int i = 0;i<paramTypes.length;i++){
            Class<?> type = paramTypes[i];
            if(type == HttpServletRequest.class ||
            type == HttpServletResponse.class){
                paramIndexMapping.put(type.getName(),i);
            }
        }

        Map<String,String[]> params = request.getParameterMap();
        Object[] paramValues = new Object[paramTypes.length];

        for(Map.Entry<String,String[]> param: params.entrySet()){
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]","")
                    .replaceAll("\\s",",");
            if(!paramIndexMapping.containsKey(param.getKey())){
                continue;
            }
            int index = paramIndexMapping.get(param.getKey());
            paramValues[index] = caseStringValue(paramTypes[index],value);

        }

        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }
        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int responseIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[responseIndex] = response;
        }
        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(), paramValues);
        if(result == null ){
            return null;
        }
        //modelAndView
        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == LrModelAndView.class;
        if(isModelAndView){
            return (LrModelAndView) result;}

        return null;
    }

    private Object caseStringValue(Class<?> paramType, String value) {
        if(Integer.class == paramType){
            return Integer.valueOf(value);
        }else if(Double.class == paramType){
            return Double.valueOf(value);
        }
        return value;

    }
}
