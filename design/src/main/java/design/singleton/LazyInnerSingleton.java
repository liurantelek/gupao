package design.singleton;

import java.io.Serializable;

/**
 * @version v1.0
 * @ProjectName: designstudydemo
 * @ClassName: LazyInnerSingleton
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/11 20:24
 */
public class LazyInnerSingleton implements Serializable {
    private LazyInnerSingleton (){
        if(LazyHolder.singleton != null){
            throw  new RuntimeException("不允许创建多个实例");
        }
    }

    public static final LazyInnerSingleton getInstance(){
        return LazyHolder.singleton;
    }

    private static class LazyHolder{
        private static final LazyInnerSingleton singleton = new LazyInnerSingleton();
    }

    private Object readResolve(){
        return LazyHolder.singleton;
    }


}
