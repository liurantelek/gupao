package com.lr.framework.webmvc.servlet;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: LrVierResolver
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/4 10:48
 */
public class LrVierResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    private File templateRootDir ;

    public LrVierResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
         templateRootDir = new File(templateRootPath);
    }

    public LrView resolvewName(String viewName, Locale locale)throws Exception{

        if(viewName == null||"".equals(viewName)){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)?viewName:viewName+DEFAULT_TEMPLATE_SUFFIX;

        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));

        return new LrView(templateFile);
    }
}
