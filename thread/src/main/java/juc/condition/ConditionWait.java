package juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: ConditionWait
 * @Description: conditionWait
 * @Author: 刘然
 * @Date: 2020/8/6 18:45
 */
public class ConditionWait implements Runnable{
    private Lock lock;

    private Condition condition;

    public ConditionWait(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public void run() {

            lock.lock();
            try {
                System.out.println("begin wait");
                condition.await();
                System.out.println("end wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

    }
}
