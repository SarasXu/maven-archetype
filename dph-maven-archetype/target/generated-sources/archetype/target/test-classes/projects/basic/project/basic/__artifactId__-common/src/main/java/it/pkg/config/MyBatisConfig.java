package it.pkg.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dph.common.utils.handler.MoneyTypeHandler;
import com.dph.common.utils.handler.QuantityTypeHandler;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.util.Properties;

/**
 * MyBatis基础配置
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {
    @Autowired
    private DruidDataSource dataSource;
    /**
     * 项目根目录包
     */
    @Value("${app.package.path}")
    private String packagePath;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //配置实体包路径
        bean.setTypeAliasesPackage(packagePath + ".entity");

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        bean.setVfs(SpringBootVFS.class);

        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});

//		bean.setTypeHandlers(MoneyTypeHandler.class);
        bean.setTypeHandlersPackage("com.dph.common.utils.handler");
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MoneyTypeHandler moneyTypeHandler() {
        return new MoneyTypeHandler();
    }

    @Bean
    public QuantityTypeHandler quantityTypeHandler() {
        return new QuantityTypeHandler();
    }
}
