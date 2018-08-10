package com.dph.temp.interceptor;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * description:
 * saras_xu@163.com 2018-01-04 12:15 创建
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface WebInterceptorUrlConfiguration {
    /**
     * 需要拦截的url
     *
     * @return
     */
    String[] include() default {"/**"};

    /**
     * 不需要拦截的url
     *
     * @return
     */
    String[] exclude() default {};

    /**
     * 优先级 拦截器拦截顺序 数字越低优先度越高
     *
     * @return
     */
    int priority() default 99;

}
