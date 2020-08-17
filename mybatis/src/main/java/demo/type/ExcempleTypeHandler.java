package demo.type;

import demo.mapper.Blog;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: ExcempleTypeHandler
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/7/16 11:11
 */
@MappedJdbcTypes(JdbcType.BLOB)
public class ExcempleTypeHandler extends BaseTypeHandler<Blog> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Blog parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i,parameter);
    }

    @Override
    public Blog getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return (Blog) rs.getObject(columnName);
    }

    @Override
    public Blog getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return (Blog) rs.getObject(columnIndex);
    }

    @Override
    public Blog getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (Blog) cs.getObject(columnIndex);
    }
}
