package juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: ConditionNotify
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/8/6 18:45
 */
public class ConditionNotify implements Runnable{

    private Lock lock;

    private Condition condition;

    public ConditionNotify(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public void run() {
        try {
            lock.lock();
            System.out.println("begin notify");
            condition.signal();//唤醒阻塞状态的线程
            System.out.println("end notify");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
