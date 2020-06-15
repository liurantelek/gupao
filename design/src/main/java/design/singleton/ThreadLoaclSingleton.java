package design.singleton;

/**
 * @version v1.0
 * @ProjectName: designstudydemo
 * @ClassName: ThreadLoaclSingleton
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/12 12:33
 */
public class ThreadLoaclSingleton {
    private static final ThreadLocal<ThreadLoaclSingleton> threadLocalSingleton = new ThreadLocal<ThreadLoaclSingleton>(){
        @Override
        protected ThreadLoaclSingleton initialValue() {
            return new ThreadLoaclSingleton();
        }
    };

    public static ThreadLoaclSingleton getInstance(){
        return threadLocalSingleton.get();
    }
}
