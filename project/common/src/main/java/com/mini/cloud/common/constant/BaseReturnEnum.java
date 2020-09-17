package com.mini.cloud.common.constant;

public enum BaseReturnEnum implements ReturnCodeEnum {

	/** 成功  */
	SUCCESS("0", "Success"),
	/** 参数为空  */
	PARAM_NULL("1", "参数为空"),
	/** 参数错误  */
	PARAM_ERROR("2", "参数错误"),
	/** 数据不存在  */
	DATA_NOT_EXISTS("3", "数据不存在"),
	/** 数据已存在  */
	DATA_EXISTS("4", "数据已存在"),
	/** 系统错误  */
	SYSTEM_ERROR("5", "系统错误"),
	
	/**微信配置错误 */
	WX_Cofig_ERROR("6","微信配置错误"),
	/** 未登录  */
	NOT_LOGIN("-3", "您尚未登录，请先登录"),
	/** 无权限*/
	NOT_AUTHC("-2","无操作权限"),
	
	;

	private String code;

	private String msg;

	private BaseReturnEnum() {
	}

	BaseReturnEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	
}
