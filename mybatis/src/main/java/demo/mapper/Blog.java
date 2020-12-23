package demo.mapper;

import lombok.Data;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: Blog
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/16 9:40
 */
@Data
public class Blog {
    private long id;
    private String text;
    private String username;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
