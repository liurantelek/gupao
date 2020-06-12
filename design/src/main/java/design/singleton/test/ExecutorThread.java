package design.singleton.test;

import design.singleton.LazyDoubleCheckSingleton;

/**
 * @version v1.0
 * @ProjectName: designstudydemo
 * @ClassName: ExecutorThread
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/11 19:58
 */
public class ExecutorThread implements Runnable {
    public void run() {
        LazyDoubleCheckSingleton singleton = LazyDoubleCheckSingleton.getInstance();
        System.out.println(singleton);
    }
}
