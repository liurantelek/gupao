package demo.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: BlogMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/16 8:58
 */
public interface BlogMapper {

    @Select("select * from blog where id=#{id}")
    Blog selectBlog(int id);
}
