package com.dph.temp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * description:此为配置类，读取properties配置属性
 * <p>
 * 组件配置的属性勿放在此类进行管理，此类只管理业务属性
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
     * 富民银行sha公钥
     */
    @Value("${fbank.sha.public.key}")
    private String fBankShaPublicKey;
    /**
     * 富民银行md5公钥
     */
    @Value("${fbank.md5.public.key}")
    private String fBankMd5PublicKey;
    /**
     * 富民银行普通代理商id
     */
    @Value("${fbank.ordinary.agent.id}")
    private String fBankOrdinaryAgentId;
    /**
     * 富民银行普通代理商key
     */
    @Value("${fbank.ordinary.agent.key}")
    private String fBankOrdinaryAgentKey;

    /**
     * 富民银行蓝海代理商id
     */
    @Value("${fbank.blue.agent.id}")
    private String fBankBlueAgentId;
    /**
     * 富民银行蓝海代理商key
     */
    @Value("${fbank.blue.agent.key}")
    private String fBankBlueAgentKey;

    /**
     * 富民银行普通代理商api接口地址
     */
    @Value("${fbank.agent.api.url}")
    private String fBankAgentApiUrl;
    /**
     * 富民银行普通代理商对账api接口地址
     */
    @Value("${fbank.agent.bill.api.url}")
    private String fBankAgentBillApiUrl;
    /**
     * 应用host
     */
    @Value("${application.local.host}")
    private String applicationLocalHost;
    /**
     * 富民银行账单文件根目录
     */
    @Value("${fbank.bill.file.root.path}")
    private String fBankBillFileRootPath;

    public String getOwner() {
        return owner;
    }

    public String getfBankShaPublicKey() {
        return fBankShaPublicKey;
    }

    public String getfBankMd5PublicKey() {
        return fBankMd5PublicKey;
    }

    public String getfBankOrdinaryAgentId() {
        return fBankOrdinaryAgentId;
    }

    public String getfBankOrdinaryAgentKey() {
        return fBankOrdinaryAgentKey;
    }

    public String getfBankAgentApiUrl() {
        return fBankAgentApiUrl;
    }

    public String getfBankAgentBillApiUrl() {
        return fBankAgentBillApiUrl;
    }

    public String getfBankBlueAgentId() {
        return fBankBlueAgentId;
    }

    public String getfBankBlueAgentKey() {
        return fBankBlueAgentKey;
    }

    public String getApplicationLocalHost() {
        return applicationLocalHost;
    }

    public String getfBankBillFileRootPath() {
        return fBankBillFileRootPath;
    }
}
