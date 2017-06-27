package it.pkg.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;

/**
 * description:数据源配置
 * saras_xu@163.com 2017-03-10 10:02 创建
 */
@Configuration
public class DataSourceConfig {
    /**
     * 数据库连接地址
     */
    @Value("${jdbc.connectionURL}")
    private String dbConnectionURL;
    /**
     * 数据库用户名
     */
    @Value("${jdbc.username}")
    private String dbUserName;
    /**
     * 数据库用户密码
     */
    @Value("${jdbc.password}")
    private String dbPassword;
    /**
     * 数据库用户驱动
     */
    @Value("${jdbc.driver}")
    private String dbDriver;

    @Primary //默认数据源
    @Bean(name = "dataSource", destroyMethod = "close")
    public DruidDataSource Construction() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        //配置数据连接的参数
        dataSource.setUrl(dbConnectionURL);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(dbDriver);

        //配置最大连接
        dataSource.setMaxActive(20);
        //配置初始连接
        dataSource.setInitialSize(1);
        //配置最小连接
        dataSource.setMinIdle(1);
        //连接等待超时时间
        dataSource.setMaxWait(60000);
        //间隔多久进行检测,关闭空闲连接
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        //一个连接最小生存时间
        dataSource.setMinEvictableIdleTimeMillis(300000);
        //用来检测是否有效的sql
        dataSource.setValidationQuery("select 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        //打开PSCache,并指定每个连接的PSCache大小
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(20);
        //配置sql监控的filter
        dataSource.setFilters("stat,wall,log4j");
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException("druid datasource init fail");
        }
        return dataSource;
    }
}
