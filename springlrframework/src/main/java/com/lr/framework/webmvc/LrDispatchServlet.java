package com.lr.framework.webmvc;

import com.lr.framework.annotion.LrController;
import com.lr.framework.annotion.LrRequestMapping;
import com.lr.framework.context.LRApplicationContext;
import com.lr.framework.webmvc.servlet.LrHandlerAdapter;
import com.lr.framework.webmvc.servlet.LrHandlerMapping;
import com.lr.framework.webmvc.servlet.LrModelAndView;
import com.lr.framework.webmvc.servlet.LrVierResolver;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: DispatchServlet
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 19:25
 */
@Slf4j
public class LrDispatchServlet extends HttpServlet {

    private LRApplicationContext context;

    private List<LrHandlerMapping> handleMappings = new ArrayList<LrHandlerMapping> ();

    private Map<LrHandlerMapping, LrHandlerAdapter> handlerAdapter = new HashMap<LrHandlerMapping,LrHandlerAdapter>();

    private List<LrHandlerMapping> handlerMappings = new ArrayList<LrHandlerMapping>();

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    List<LrVierResolver> viewResovlers = new ArrayList<LrVierResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        LrHandlerMapping handler = getHandler(req);
        if(handler == null){
            //返回404界面
            return ;
        }

        LrHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        //真正的调用方法
        LrModelAndView modelAndView = handlerAdapter.handle(req,resp,handler);

        processDispatchResult(req,resp,modelAndView);
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, LrModelAndView modelAndView) {
        //将modevandview转化成html outputstream 或者json或者freemark veolcity
        //contexttype
        if(null == modelAndView){
            return;
        }
    }

    private LrHandlerAdapter getHandlerAdapter(LrHandlerMapping handler) {
        if(this.handlerAdapter.isEmpty()){
            return null;
        }
        LrHandlerAdapter handlerAdapter = this.handlerAdapter.get(handler);
        if(handlerAdapter.support(handler)){
            return handlerAdapter;
        }
        return null;
    }

    private LrHandlerMapping getHandler(HttpServletRequest req){
        if(handleMappings.isEmpty()){
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");
        for(LrHandlerMapping handlerMapping : handleMappings){
            Matcher matcher = handlerMapping.getPattern().matcher(url);
            if(matcher.matches()){
               return handlerMapping;
            }
        }
        return null;
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
        //handlermapping
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
                if(!clazz.isAnnotationPresent(LrController.class)){
                   continue;
                }
                String baseUrl = "";
                if(clazz.isAnnotationPresent (LrRequestMapping.class)){
                    LrRequestMapping requestMapping = clazz.getAnnotation (LrRequestMapping.class);
                    baseUrl = requestMapping.value ();
                }
                for(Method m : clazz.getMethods ()){
                    if(!m.isAnnotationPresent (LrRequestMapping.class)){
                        continue;
                    }
                    LrRequestMapping requestMapping = m.getAnnotation (LrRequestMapping.class);
                    String regex = ("/"+baseUrl + "/"+requestMapping.value ().replaceAll("\\*",".*")).replaceAll ("/+","/");
                    Pattern pattern = Pattern.compile(regex);
                    handleMappings.add (new LrHandlerMapping (pattern,bean,m));
                    log.info("mapped:"+regex);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initFlashManager(LRApplicationContext context) {
    }

    private void initViewResolvers(LRApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String fileRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(fileRootPath);
        for(File file : templateRootDir.listFiles()){
            this.viewResovlers.add(new LrVierResolver(templateRoot));
        }

    }

    private void initRequestToViewTranslator(LRApplicationContext context) {
        
    }

    private void initHandlerExceptionResolver(LRApplicationContext context) {
        
    }

    private void initHandlerAdapters(LRApplicationContext context) {
        //把一个request请求变成一个handler，参数都是字符串的，自动配置到handler的形参
        //只有拿到handermapping才能进行操作
        for(LrHandlerMapping handlerMapping : handleMappings){

        }
    }

    private void initThemeResolver(LRApplicationContext context) {
    }

    private void initLocalResolver(LRApplicationContext context) {
    }

    private void initMultipartResolver(LRApplicationContext context) {
        
    }

   
}
