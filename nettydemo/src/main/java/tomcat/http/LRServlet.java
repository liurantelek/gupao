package tomcat.http;

/**
 * @author liuran
 * @create 2020-09-07-22:42
 */
public abstract class LRServlet   {
    public abstract void service(LRRequest request,LRResponse response);

    public abstract void doGet(LRRequest request,LRResponse response);

    public abstract void doPost(LRRequest request,LRResponse response);
}
