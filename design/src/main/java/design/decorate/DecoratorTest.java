package design.decorate;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: DecoratorTest
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/18 22:03
 */
public class DecoratorTest {

    public static void main(String[] args) {
        BatterCake batterCake = new BaseBatterCake();
        batterCake = new EggDecorator(batterCake);
        batterCake = new EggDecorator(batterCake);
        System.out.println(batterCake.getMsg());
        System.out.println(batterCake.getPrice());
    }
}
