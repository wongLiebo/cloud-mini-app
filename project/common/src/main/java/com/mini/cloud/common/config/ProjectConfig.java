package com.mini.cloud.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="project.config")
@Getter
@Setter
public class ProjectConfig {
	
	/**公司编号*/
	private String companyNo="TP";
	
	/**项目名称*/
	private String appName="Test";
	
	/**国家编号*/
	private String nationNo="86";
	
	/**是否单点登录*/
	private boolean isSSO=false;
	
	/**是否短信真实发送*/
	private boolean isSMSSend=false;
	
	/**特殊验证码的手机号*/
	private String vCodeMobile="19945754648";
	
//	/**使用oss类型【aliyun ,file】*/
//	private String oss="aliyun";
	
	/**项目模式*/
	private String projectMode="B2C";
	
	
	/**普通公司使用数据类型【使用自己数据，使用平台公司数据】*/
	private String useDataType="";
	
	/**微信小程序AppID*/
	private String wxAppID;
	/**微信小程序证书*/
	private String wxAppSecret;
	
	/**MQ类型*/
	private String mqType;
	
	/**0-31*/
	private long workerId;
	/**0-31*/
	private long datacenterId;
	
	
}
