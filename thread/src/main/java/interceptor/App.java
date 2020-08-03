package interceptor;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: interceptor.App
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/22 20:46
 */
public class App {
   static Interceptor interceptor;

    public static void main(String[] args) {
        App app  = new App();
        app.setUp();
        Request request = new Request();
        request.setName("sss");

        interceptor.proceed(request);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("bbbb");

    }

    private  void setUp(){
        PrintInterceptor printInterceptor = new PrintInterceptor();
        printInterceptor.start();
        SaveInterceptor saveInterceptor = new SaveInterceptor(printInterceptor);
        saveInterceptor.start();
        interceptor = new PreInterceptor(saveInterceptor);
        ((PreInterceptor) interceptor).start();
        ((PreInterceptor) interceptor).interrupt();
        Thread.interrupted();


    }
}
