package demo;

import com.mysql.cj.Session;
import com.mysql.cj.jdbc.MysqlDataSource;
import demo.mapper.BlogMapper;
import jdk.management.resource.ResourceRequest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: CreateSqlSessionFactory
 * @Description: sqlSessionFactory应该创建全局的 运行期间一直存在的
 * @Author: 刘然
 * @Date: 2020/7/16 8:55
 */
public class MySqlSessionFactory {

    private static final String MYBATIS_CONFIG_URL="mybatis/example/mybatis-config.xml";

    /**
     * 获取sqlSessionFactory
     * @return
     */
    public static SqlSessionFactory getSqlSessionFactoryFromClass(){
        DataSource dataSource = new MysqlDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development",transactionFactory,dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactoryFromConfig(){
        try {
            InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG_URL);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            return sqlSessionFactory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    public static SqlSession getSession(){
        SqlSessionFactory sqlSessionFactoryFromConfig = getSqlSessionFactoryFromConfig();
        SqlSession sqlSession = sqlSessionFactoryFromConfig.openSession();
        return sqlSession;
    }

    public static <T> T getMapper(Class<T> clazz){
        SqlSession session = getSession();
        T mapper = session.getMapper(clazz);
        return mapper;
    }

}
