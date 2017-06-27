package it.pkg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * description:此为配置类，读取properties配置属性
 * <p>
 * 组件配置的属性勿放在此类进行管理，组件配置加载顺序难以控制，有风险，所以此类只管理业务属性
 * </p>
 * saras_xu@163.com 2017-03-10 10:31 创建
 */
@Configuration
public class ConfigProperty {
    /**
     * 系统所有者
     */
    @Value("${app.owner}")
    private String owner;

    /**
     * 系统当前运行环境是否为开发环境
     */
    @Value("${isdev}")
    private boolean isDev;

    public boolean isDev() {
        return isDev;
    }

    public String getOwner() {
        return owner;
    }

}
