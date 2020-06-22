package design.decorate;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: EggDecorator
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/18 22:02
 */
public class EggDecorator extends BatterCakeDecorator {

    public EggDecorator(BatterCake batterCake) {
        super(batterCake);
    }

    @Override
    protected String getMsg() {
        return super.getMsg()+"1个鸡蛋";
    }

    @Override
    protected int getPrice() {
        return super.getPrice()+1;
    }
}
