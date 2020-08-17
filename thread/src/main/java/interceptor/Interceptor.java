package interceptor;

/**
 * @Auther: 45417
 * @Date: 2020/7/22 20:47
 * @Description:
 */
public interface Interceptor {

    void proceed(Request request);
}
