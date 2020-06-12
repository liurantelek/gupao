package design.singleton;

/**
 * @version v1.0
 * @ProjectName: designstudydemo
 * @ClassName: LazySingleton
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/11 19:54
 */
public class LazySingleton {

    public static volatile LazySingleton lazySingleton = null;
    private LazySingleton(){}

    public static  LazySingleton getInstance(){

        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
