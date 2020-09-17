package com.mini.cloud.common.exception;


import com.mini.cloud.common.constant.ReturnCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 异常类
 */
@Getter
@Setter
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private String code = "500";
	/**
	 * 错误信息
	 */
	private String msg;
	/**
	 * 其他额外信息
	 */
	private String extra;

	public BusinessException(ReturnCodeEnum returnCode) {
		super(returnCode.getMsg());
		this.code = returnCode.getCode();
		this.msg = returnCode.getMsg();
	}
	
	
	
	public BusinessException(ReturnCodeEnum returnCode, String msg) {
		super(msg);
		this.code = returnCode.getCode();
		this.msg = msg;
	}

	public BusinessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public BusinessException(String code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public BusinessException(String code, String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public BusinessException(String code, String msg, String extra) {
		super(msg);
		this.msg = msg;
		this.code = code;
		this.extra = extra;
	}


}
