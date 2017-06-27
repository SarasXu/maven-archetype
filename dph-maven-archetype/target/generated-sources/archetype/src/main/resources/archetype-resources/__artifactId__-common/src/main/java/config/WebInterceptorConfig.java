#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import com.dph.common.utils.interceptor.CrossDomainInterceptor;
import com.dph.common.utils.interceptor.ParameterInformationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * description:拦截器配置 saras_xu@163.com 2017-04-18 11:42 创建
 */
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ConfigProperty configProperty;

    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // 参数拦截
        registry.addInterceptor(new ParameterInformationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new CrossDomainInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);

    }
}
