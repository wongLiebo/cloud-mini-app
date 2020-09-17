package com.mini.cloud.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhuweimin
 */
@Data
@ConfigurationProperties(prefix = "sms")
@Component
public class SmsProperties {

    /**
     * 系统来源 FROM_DY
     */
    private String fromID;
    /**
     * 机构编码字段 1
     */
    private String branchID;
    /**
     * 服务类型IDDSWX_ZFJG
     */
    private String serviceID;
    /**
     * 集团成员 ORG_TPRS
     */
    private String orgID;

    private String channelID;
    /**
     * 默认为长短信,1为长短信
     */
    private String lengthSMFLag;
    /**
     * 保信通登录用户名
     */
    private String username;
    /**
     * 保信通登录密码
     */
    private String password;

}
