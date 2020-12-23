package demo.session;

import demo.MySqlSessionFactory;
import demo.mapper.Blog;
import demo.mapper.BlogMapper;

import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: Service
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/16 16:59
 */
public class Service {


    public static void main(String[] args) {
        test();
    }

    public static String test(){
        BlogMapper mapper = MySqlSessionFactory.getMapper(BlogMapper.class);
        Blog blogaaa = mapper.selectBlog("1");
        System.out.println(blogaaa.toString());
//        boolean b = mapper.deleteBolg("1");
//        List<Blog> blogaaa = mapper.selectBlogAll();
//        System.out.println(blogaaa.toString());
//        return blogaaa.toString();
        return null;
    }
}
