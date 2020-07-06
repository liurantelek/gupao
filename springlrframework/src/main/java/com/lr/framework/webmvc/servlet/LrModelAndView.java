package com.lr.framework.webmvc.servlet;

import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: ModelAndView
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/1 20:43
 */
public class LrModelAndView {


    private String viewName;

    private Map<String,?> model;

    public LrModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public LrModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
