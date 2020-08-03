package interceptor;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: interceptor.PreInterceptor
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/22 20:47
 */
public class SaveInterceptor extends Thread implements Interceptor {

    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();

    private  Interceptor interceptor;

    private volatile boolean isFinish = false;


    public SaveInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void shutDown(){
        isFinish = true;
    }

    public SaveInterceptor() {
    }

    @Override
    public void run() {
       while (!isFinish){
           try {
               Request request = requests.take();
               System.out.println("preInterceptor:"+request);
               interceptor.proceed(request);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    public void proceed(Request request) {
        requests.add(request);
    }
}
