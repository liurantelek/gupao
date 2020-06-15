package design.template.jdbc;

import com.sun.rowset.internal.Row;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: JdbcTemplate
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/15 12:14
 */
public class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<?> executeQuery(String sql, RowMapper<?> rowMapper,Object[] values){

        //获取连接
        List<?> list = null;
        try {
            Connection connection = this.getConnection();
            //创建语句集
            PreparedStatement pstm = this.createPreparedStatement(connection,sql);
            ResultSet resultSet = this.executeQuery(pstm,values);
            list = this.parseResultSet(resultSet,rowMapper);
            this.closeResultSet(resultSet);
            this.closeStatement(pstm);
            this.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeStatement(PreparedStatement pstm) {
        try {
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<?> parseResultSet(ResultSet resultSet,RowMapper<?> rowMapper) throws SQLException {
        List<Object> list = new ArrayList<Object>();
        int rowNum = 1;
        try {
            while (resultSet.next()) {
                list.add(rowMapper.mapRow(resultSet, rowNum));
                rowNum++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private ResultSet executeQuery(PreparedStatement pstm, Object[] values) {
        for(int i = 0;i<values.length;i++){
            try {
                pstm.setObject(i+1,values[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            return pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    private  PreparedStatement createPreparedStatement(Connection connection, String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
