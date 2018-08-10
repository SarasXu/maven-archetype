package com.dph.temp.service;

import java.lang.annotation.*;

/**
 * description:
 *
 * @author saras_xu@163.com
 * @date 2018-01-22 10:16 创建
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ServiceHandler {
    /**
     * 是否添加事务，默认不添加 false
     *
     * @return
     */
    boolean isTransaction() default false;

    /**
     * 是否检查参数，默认检查 trues
     *
     * @return
     */
    boolean isCheckOrder() default true;
}
