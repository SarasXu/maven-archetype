package com.dph.temp.base;

import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * description:
 * saras_xu@163.com 2017-04-19 13:59 创建
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTestBase implements ApplicationContextAware, EnvironmentAware {

    protected ApplicationContext applicationContext;
    protected Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

}
