package tomcat.servlet;

import tomcat.http.LRRequest;
import tomcat.http.LRResponse;
import tomcat.http.LRServlet;

/**
 * @author liuran
 * @create 2020-09-07-22:46
 */
public class FirstServlet extends LRServlet {


    @Override
    public void service(LRRequest request, LRResponse response) {
        doPost (request,response);
    }

    public void doGet(LRRequest request, LRResponse response) {
        this.doPost (request,response);
    }

    public void doPost(LRRequest request, LRResponse response) {
        response.write("s");
    }
}
