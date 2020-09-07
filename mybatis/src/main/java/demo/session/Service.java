package demo.session;

import demo.MySqlSessionFactory;
import demo.mapper.Blog;
import demo.mapper.BlogMapper;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: Service
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/16 16:59
 */
public class Service {

    public String test(){
        BlogMapper mapper = MySqlSessionFactory.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlog(101);
        return blog.toString();
    }
}
