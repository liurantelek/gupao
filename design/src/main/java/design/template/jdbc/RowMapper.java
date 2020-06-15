package design.template.jdbc;


import java.sql.ResultSet;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: RowMapper
 * @Description: orm
 * @Author: 刘然
 * @Date: 2020/6/15 12:12
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs,int rowNum) throws Exception;
}
