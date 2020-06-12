package design.singleton;

/**
 * @version v1.0
 * @ProjectName: designstudydemo
 * @ClassName: LazyDoubleCheckSingleton
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/11 20:14
 */
public class LazyDoubleCheckSingleton {

    public static  LazyDoubleCheckSingleton lazyDoubleCheckSingleton ;

    private LazyDoubleCheckSingleton (){}

    public static LazyDoubleCheckSingleton getInstance(){
        if(lazyDoubleCheckSingleton == null){
            synchronized (LazyDoubleCheckSingleton.class){
                if(lazyDoubleCheckSingleton == null){
                    lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();
                }
            }
        }
        return lazyDoubleCheckSingleton;
    }
}
