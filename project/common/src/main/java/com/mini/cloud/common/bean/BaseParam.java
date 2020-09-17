package com.mini.cloud.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础参数
 */
@Setter
@Getter
public class BaseParam {



	@ApiModelProperty(value = "语言分类", example = "zh")
	private String langType="zh" ;
	
	@ApiModelProperty(value = "签名")
	private String sign;
	
	@ApiModelProperty(value = "签名类型[MD5/SHA256]")
	private String signType="MD5";
	
	@ApiModelProperty(value = "随机字符串")
	private String nonceStr;

	@ApiModelProperty(value = "版本号")
	private String version;

	@ApiModelProperty(value = "用户令牌")
	private  String UserToken;

	public void check() {
	}
	
}
