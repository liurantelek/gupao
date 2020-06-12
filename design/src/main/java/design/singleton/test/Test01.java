package design.singleton.test;

import design.singleton.LazyInnerSingleton;

import java.io.*;

/**
 * @version v1.0
 * @ProjectName: designstudydemo
 * @ClassName: Test01
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/11 20:22
 */
public class Test01 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new ExecutorThread());
        Thread thread2 = new Thread(new ExecutorThread());
        LazyInnerSingleton singleton = LazyInnerSingleton.getInstance();
        thread1.start();
        thread2.start();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("a.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(singleton);
            oos.flush();
            oos.close();
            fos.flush();
            fos.close();

            FileInputStream fis = new FileInputStream("a.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            ois.close();
            fis.close();
            System.out.println(o);
            System.out.println(singleton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
