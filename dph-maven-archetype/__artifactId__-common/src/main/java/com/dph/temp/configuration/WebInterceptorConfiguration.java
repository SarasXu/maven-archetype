package com.dph.temp.configuration;

import com.dph.common.utils.interceptor.CrossDomainInterceptor;
import com.dph.common.utils.interceptor.ParameterInformationInterceptor;
import com.dph.sale.interceptor.AbstractInterceptorHandler;
import com.dph.sale.interceptor.WebInterceptorUrlConfiguration;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Map;

/**
 * description:
 * saras_xu@163.com 2018-01-04 11:41 创建
 */
@Component
public class WebInterceptorConfiguration extends WebMvcConfigurerAdapter implements InitializingBean, ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(WebInterceptorConfiguration.class);
    /**
     * 拦截器排序最小个数
     */
    private final static int MIN_SORT_SIZE = 2;
    private ApplicationContext applicationContext;
    private List<AbstractInterceptorHandler> interceptors = Lists.newArrayList();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, AbstractInterceptorHandler> interceptorMap = this.applicationContext.getBeansOfType(AbstractInterceptorHandler.class);
        for (Map.Entry<String, AbstractInterceptorHandler> entry : interceptorMap.entrySet()) {
            interceptors.add(entry.getValue());
        }
        logger.info("需要加载的拦截器个数：{}", interceptors.size());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ParameterInformationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new CrossDomainInterceptor()).addPathPatterns("/**");
        //根据优先级进行排序
        if (interceptors.size() >= MIN_SORT_SIZE) {
            interceptors = sort(interceptors);
        }
        for (AbstractInterceptorHandler interceptor : interceptors) {
            WebInterceptorUrlConfiguration webInterceptorUrl = interceptor.getClass().getAnnotation(WebInterceptorUrlConfiguration.class);
            registry.addInterceptor(interceptor).addPathPatterns(webInterceptorUrl.include()).excludePathPatterns(webInterceptorUrl.exclude());
        }
        super.addInterceptors(registry);
    }

    private List<AbstractInterceptorHandler> sort(List<AbstractInterceptorHandler> interceptors) {
        interceptors.sort((o1, o2) -> {
            //根据拦截器优先级排序
            return o1.getClass().getAnnotation(WebInterceptorUrlConfiguration.class).priority() > o2.getClass().getAnnotation(WebInterceptorUrlConfiguration.class).priority() ? 1 : -1;
        });
        return interceptors;

    }
}
