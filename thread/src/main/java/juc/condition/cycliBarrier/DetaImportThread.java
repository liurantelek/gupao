package juc.condition.cycliBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: DetaImportThread
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/8/8 12:37
 */
public class DetaImportThread extends Thread {

    private CyclicBarrier cyclicBarrier;

    private String path;
    public DetaImportThread(CyclicBarrier cyclicBarrier,String path) {
        this.cyclicBarrier = cyclicBarrier;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(path+"导入完成");
    }
}
