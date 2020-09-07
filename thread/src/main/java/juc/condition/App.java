package juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: App
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/8/6 18:49
 */
public class App {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(new ConditionWait(lock,condition)).start();
        new Thread(new ConditionNotify(lock,condition)).start();
    }
}
