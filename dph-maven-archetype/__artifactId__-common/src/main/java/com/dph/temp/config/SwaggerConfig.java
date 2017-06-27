package com.dph.temp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <font color="green">Swagger配置</font>
 *
 * @author HongShaowei 18723728730 hongsw@168.com
 * @ClassName com.dph.finance.config.SwaggerConfig
 * @date 2017年5月15日 下午6:12:31
 */
@Configuration
@EnableSwagger2
//@Profile(value = {"test", "dev", "local"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // .apis(RequestHandlerSelectors.basePackage("com.dph.finance.common"))//扫描此包
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))// 只扫描这个注解
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("b1-finance微服务系统API接口")
                .description("SwaggerConfig配置:HongShaowei,如有疑问联系QQ:997394672")
                .contact("DPH_HongShaowei")
                .version("1.0")
                .build();
    }
}
