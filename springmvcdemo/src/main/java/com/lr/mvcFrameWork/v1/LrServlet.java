package com.lr.mvcFrameWork.v1;

import com.lr.mvcFrameWork.v1.annotion.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuran
 * @create 2020-06-20-17:57
 */
public class LrServlet extends HttpServlet{

    private Properties contextConfig = new Properties ();

    //用于保存所有的类名
    private List<String> classNames = new ArrayList<String> ();

//    private Map<String,Method> handlerMapping = new ConcurrentHashMap<String,Method> ();

    private List<HandleMapping> handleMappings = new ArrayList<HandleMapping> ();

    //ioc容器
    private Map<String,Object> ioc = new ConcurrentHashMap<String, Object> ();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost (req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doDisPatch(req,resp);
    }

    private void doDisPatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
        String url = req.getRequestURI ();
        String contextPath = req.getContextPath ();
        url = url.replaceAll (contextPath,"").replaceAll ("/+","/");
        HandleMapping handler = getHandleMapping(url);
        if(handler == null){
            resp.getWriter ().write ("404 Not found");
        }
        Method m =  handler.getMethod ();
        m.setAccessible (true);
        String beanName = toLowerFirstCase (m.getDeclaringClass ().getSimpleName ());
            //获得方法的形参列表
            Class<?> [] paramTypes = handler.getParamTypes();

            Object [] paramValues = new Object[paramTypes.length];

            Map<String,String[]> params = req.getParameterMap();
            for (Map.Entry<String, String[]> parm : params.entrySet()) {
                String value = Arrays.toString(parm.getValue()).replaceAll("\\[|\\]","")
                        .replaceAll("\\s",",");

                if(!handler.paramIndex.containsKey(parm.getKey())){continue;}

                int index = handler.paramIndex.get(parm.getKey());
                paramValues[index] = convert(paramTypes[index],value);
            }

            if(handler.paramIndex.containsKey(HttpServletRequest.class.getName())) {
                int reqIndex = handler.paramIndex.get(HttpServletRequest.class.getName());
                paramValues[reqIndex] = req;
            }

            if(handler.paramIndex.containsKey(HttpServletResponse.class.getName())) {
                int respIndex = handler.paramIndex.get(HttpServletResponse.class.getName());
                paramValues[respIndex] = resp;
            }

        Object o = m.invoke (ioc.get (beanName),paramValues);
        resp.getWriter ().write (o.toString ());
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private Object convert(Class<?> type,String value){
        //如果是int
        if(Integer.class == type){
            return Integer.valueOf(value);
        }
        else if(Double.class == type){
            return Double.valueOf(value);
        }
        //如果还有double或者其他类型，继续加if
        //这时候，我们应该想到策略模式了
        //在这里暂时不实现，希望小伙伴自己来实现
        return value;
    }

    private HandleMapping getHandleMapping(String url) {
        if(url == null)return null;
        if(handleMappings.isEmpty ())return null;
        for(HandleMapping h : handleMappings){
            if(h.getUrl ().equals (url)){
                return h;
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、加载配置文件
        doLoadConfig(config.getInitParameter ("contextConfigLocation"));
        //2、扫描相关的类
        doScanner(contextConfig.getProperty ("scanPackage"));
        //3、初始化扫描到的类，并且将他们放入到ioc容器中
        doInstance();
        //4、完成依赖注入
        doAutoWired();
        //5、初始化handleMapping
        initHandleMapping();
        //6、初始化完成调用
    }


    private void initHandleMapping() {
        if(ioc.isEmpty ())return;
        for(Map.Entry<String,Object> entry : ioc.entrySet ()){
            Class<?> clazz = entry.getValue ().getClass ();
            if(!clazz.isAnnotationPresent (LrController.class))continue;
            String baseUrl = "";
            if(clazz.isAnnotationPresent (LrRequestMapping.class)){
                LrRequestMapping requestMapping = clazz.getAnnotation (LrRequestMapping.class);
                baseUrl = requestMapping.value ();
            }
            for(Method m : clazz.getMethods ()){
                if(!m.isAnnotationPresent (LrRequestMapping.class))continue;
                LrRequestMapping requestMapping = m.getAnnotation (LrRequestMapping.class);
                String url = ("/"+baseUrl + "/"+requestMapping.value ()).replaceAll ("/+","/");
                handleMappings.add (new HandleMapping (url,m,entry.getValue()));
            }
        }
    }

    private void doAutoWired() {
        if(ioc.isEmpty ())return;
        for(Map     .Entry<String,Object> entry : ioc.entrySet ()){
            //declard所以的特定的字段
            Field[] fields = entry.getValue ().getClass ().getDeclaredFields ();
            for(Field field : fields){
                field.setAccessible (true);
                if(field.isAnnotationPresent (LrAutowired.class)){
                    LrAutowired autowired = field.getAnnotation (LrAutowired.class);
                    String beanName = autowired.value ().trim ();
                    if("".equals (beanName)){
                        beanName = toLowerFirstCase (field.getType ().getSimpleName ());
                    }
                    try {
                        field.set (entry.getValue (),ioc.get (beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace ();
                    }
                }


            }
        }
    }

    private void doInstance() {
        if(classNames.isEmpty ()){
            return;
        }
        for(String className : classNames){
            try {
                Class<?> clazz = Class.forName (className);
                //什么样的类初始化，加了注解的类初始化
                if(clazz.isAnnotationPresent (LrController.class)||
                        clazz.isAnnotationPresent (LrService.class)){
                    Object instance = clazz.newInstance ();
                    String beanName = toLowerFirstCase(clazz.getSimpleName ());
                    ioc.put (beanName,instance);
                }
                if(clazz.isAnnotationPresent (LrService.class)){
                    //1、默认类名小写
                    //2、自定义的beanName
                    //3、根据类型自动复制
                    String beanName = toLowerFirstCase (clazz.getSimpleName ());
                    LrService service = clazz.getAnnotation (LrService.class);
                    if(!"".equals (service.value ())){
                        beanName = service.value ();
                    }
                    Object instance = clazz.newInstance ();
                    ioc.put (beanName,instance);
                    for(Class<?> c : clazz.getInterfaces ()){
                        if(ioc.containsKey (c.getName ())){
                            throw new Exception ("the"+c.getName ()+"is exists");
                        }
                        ioc.put (toLowerFirstCase (c.getSimpleName ()),instance);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray ();
        if(chars[0]>'A' && chars[0]<'Z'){
        chars[0] += 32;}
        return String.valueOf (chars);
    }


    //扫描出相关的类
    private void doScanner(String scanPackage) {
        URL url = this.getClass ().getClassLoader ().getResource ("/"+scanPackage.replaceAll ("\\.","/"));
        File classPath = new File (url.getFile ());
        for(File file : classPath.listFiles ()){
            if(file.isDirectory ()){
                doScanner (scanPackage+"."+file.getName ());
            }else {
                if(!file.getName ().endsWith (".class")){
                    continue;
                }
                //类名
                String className = (scanPackage+"."+file.getName ().replace (".class",""));
                classNames.add (className);
            }
        }
    }

    /**
     * 加载配置文件
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {
        //直接从类路径下找到spring的主配置文件，并且将读取出来放置到properties中
        InputStream is = this.getClass ().getClassLoader ().getResourceAsStream (contextConfigLocation);
        try {
            contextConfig.load (is);
        } catch (IOException e) {
            e.printStackTrace ();
        }finally {
            if(is != null){
                try {
                    is.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }

    }

    public class HandleMapping{
       private String url;

       Method method;

       Object controller;

       Map<String,Integer> paramIndex ;

       private Class<?>[] paramTypes ;

        public HandleMapping(String url, Method method, Object controller) {
            this.url = url;
            this.method = method;
            this.controller = controller;
            this.paramIndex = putParamIndex(method);

        }

        public String getUrl() {
            return url;
        }

        public Method getMethod() {
            return method;
        }

        public Object getController() {
            return controller;
        }

        public Class<?>[] getParamTypes() {
            return paramTypes;
        }

        private Map<String,Integer> putParamIndex(Method method) {
            Map<String,Integer> paramIndex = new HashMap<String, Integer> ();

            Annotation[][] annotations = method.getParameterAnnotations ();
            for (int j = 0; j < annotations.length; j++) {
                for (Annotation a : annotations[j]) {
                    if (a instanceof LrRequestParam) {
                        String paramName = ((LrRequestParam) a).value ();
                        if (!"".equals (paramName)) {
                            paramIndex.put (paramName, j);
//                                }
                        }
                    }
                }
            }

            Class<?>[] paramTypes = method.getParameterTypes ();
            this.paramTypes = paramTypes;
            for(int i=0;i<paramTypes.length;i++){
                Class<?> type = paramTypes[i];
                if(type == HttpServletRequest.class || type == HttpServletResponse.class){
                    paramIndex.put (type.getName (),i);
                }
            }
            return paramIndex;
        }
    }
}
