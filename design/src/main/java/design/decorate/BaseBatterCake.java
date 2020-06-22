package design.decorate;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: BaseBatterCake
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/18 21:58
 */
public class BaseBatterCake extends BatterCake {

    @Override
    protected String getMsg() {
        return "煎饼";
    }

    @Override
    protected int getPrice() {
        return 5;
    }
}
