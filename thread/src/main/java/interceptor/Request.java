package interceptor;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: interceptor.Request
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/22 21:00
 */
public class Request {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "interceptor.Request{" +
                "name='" + name + '\'' +
                '}';
    }
}
