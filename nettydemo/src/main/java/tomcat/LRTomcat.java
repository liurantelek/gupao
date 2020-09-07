package tomcat;

import tomcat.http.LRRequest;
import tomcat.http.LRResponse;
import tomcat.http.LRServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author liuran
 * @create 2020-09-07-22:30
 */
public class LRTomcat {
    /**
     * 配置好启动端口
     * 2、配置web.xml,自己写的servlet集成httpServlet
     * 3、servlet-name
     * 4、servlet-class
     * url-pattern
     * 3、读取配置 url-pattern和servlet建立映射关系
     * servletMapping
     * 4、HTTP协议发送的数据是字符串，有规律的字符串
     * 5、从协议内容中拿到url，把相应的servlet用反射实例化
     * 6、调用实例化的对象的server方法，执行具体的方法doGet（）和doPost方法
     */

    private int port = 8080;

    private ServerSocket server;

    private Map<String, LRServlet> servletMapping = new HashMap<> ();

    private Properties webXml = new Properties ();

    private void init() {
        String path = this.getClass ().getClassLoader ().getResource ("/").getPath ();
        try {
            FileInputStream fileInputStream = new FileInputStream (path + "web.properties");
            try {
                webXml.load (fileInputStream);

                for (Object k : webXml.keySet ()) {
                    String key = k.toString ();
                    if (key.startsWith (".url")) {
                        String servletName = key.replaceAll ("\\.url$", "");
                        String url = webXml.getProperty (key);
                        String className = webXml.getProperty (servletName + ".className");
                        LRServlet servlet = (LRServlet) Class.forName (className).newInstance ();
                        servletMapping.put (url, servlet);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace ();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
    }

    public void start(){
        try {
            server = new ServerSocket (this.port);
            while (true){
                Socket client = server.accept ();
                process(client);
            }

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void process(Socket client) {

        try {
            InputStream inputStream = client.getInputStream ();
            OutputStream outputStream = client.getOutputStream ();
            LRRequest request =  new LRRequest (inputStream);
            LRResponse response = new LRResponse (outputStream);
            String url = request.getURL();
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

}
