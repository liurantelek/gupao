package design.singleton;

/**
 * @version v1.0
 * @ProjectName: designstudydemo
 * @ClassName: Hungry
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/11 19:05
 */
public class Hungry {
    private Hungry() {
    }

    public static final Hungry hungry = new Hungry();

    public static Hungry getInstance(){
        return hungry;
    }
}
