package juc.condition.cycliBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: CycliBarrierDemo
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/8/8 12:36
 */
public class CycliBarrierDemo extends Thread{

    @Override
    public void run() {
        System.out.println("开始数据分析");
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new CycliBarrierDemo());
        new Thread(new DetaImportThread(cyclicBarrier,"path1")).start();
        new Thread(new DetaImportThread(cyclicBarrier,"path2")).start();
        new Thread(new DetaImportThread(cyclicBarrier,"path3")).start();
    }
}
