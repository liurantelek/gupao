package design.decorate;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: BatterCakeDecorator
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/18 22:00
 */
public abstract class BatterCakeDecorator extends BatterCake{
    private BatterCake batterCake;

    public BatterCakeDecorator(BatterCake batterCake) {
        this.batterCake = batterCake;
    }

    @Override
    protected String getMsg() {
        return this.batterCake.getMsg();
    }

    @Override
    protected int getPrice() {
        return this.batterCake.getPrice();
    }
}
