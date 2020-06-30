package com.lr.framework.webmvc;

import com.lr.framework.annotion.LrController;
import com.lr.framework.context.LRApplicationContext;
import com.lr.framework.webmvc.servlet.LrHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: DispatchServlet
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 19:25
 */
public class LrDispatchServlet extends HttpServlet {

    private LRApplicationContext context;

    private List<LrHandlerMapping> handlerMappings = new ArrayList<LrHandlerMapping>();

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doDispatch(req,resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化application容器
        context = new LRApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //初始化spring mvc 的9大组件
        initStrategies(context);
        //
    }
    
    protected void initStrategies(LRApplicationContext context){
        //多文件上传组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocalResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        initHandlerMappings(context);
        //初始话参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolver(context);
        //初始化视图预处理器
        initRequestToViewTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
        //初始化flash管理器
        initFlashManager(context);
    }

    private void initHandlerMappings(LRApplicationContext context) {
        String [] beanNames = context.getBeanDefinitionNames();
        for(String beanName : beanNames){
            try {
                Object bean = context.getBean(beanName);
                Class<?> clazz = bean.getClass();
                if(clazz.isAnnotationPresent(LrController.class)){
                    Method[] methods = clazz.getMethods();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initFlashManager(LRApplicationContext context) {
    }

    private void initViewResolvers(LRApplicationContext context) {
        
    }

    private void initRequestToViewTranslator(LRApplicationContext context) {
        
    }

    private void initHandlerExceptionResolver(LRApplicationContext context) {
        
    }

    private void initHandlerAdapters(LRApplicationContext context) {
    }

    private void initThemeResolver(LRApplicationContext context) {
    }

    private void initLocalResolver(LRApplicationContext context) {
    }

    private void initMultipartResolver(LRApplicationContext context) {
        
    }
}
