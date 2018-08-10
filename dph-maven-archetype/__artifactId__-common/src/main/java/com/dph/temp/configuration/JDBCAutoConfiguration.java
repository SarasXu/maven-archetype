package com.dph.temp.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableConfigurationProperties({DruidProperties.class})
@ConditionalOnProperty(value = "meeting.ds.enable", matchIfMissing = true)
@EnableAutoConfiguration
public class JDBCAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(JDBCAutoConfiguration.class);

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DruidDataSource dataSource() {
        try {
            if (druidProperties == null) {
                return DruidProperties.buildFromEnv(DruidProperties.PREFIX);
            } else {
                return druidProperties.build();
            }
        } catch (Exception e) {
            //这种方式有点挫，先就这样吧
            logger.error("初始化数据库连接池异常，关闭应用", e);
            System.exit(0);
            throw new RuntimeException("数据源配置异常", e);
        }
    }

    @Bean
    @ConditionalOnMissingBean(JdbcOperations.class)
    public JdbcTemplate jdbcTemplate(DruidDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(NamedParameterJdbcOperations.class)
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DruidDataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(TransactionTemplate.class)
    public TransactionTemplate transactionTemplate(@Qualifier("annotationDrivenTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

}
