package com.mini.cloud.common.constant;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;

public class BaseConstant {

	
	/**
	 * 是否有效 1：有效 0：失效
	 */
	public enum EnabledEnum implements BaseCodeEnum {
		ENABLED("1","有效"),
		DISABLED("0","失效");
		private String code;
		private String msg;
		private EnabledEnum(String code,String msg) {
			this.code=code;
			this.msg=msg;
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

	/**
	 * 是否 1：是 0：否
	 */
	public enum YNEnum implements BaseCodeEnum {
		YES("1","是"),
		NO("0","否");

		private String code;
		private String msg;

		private YNEnum() {
		}

		private YNEnum(String code,String msg) {
			this.code = code;
			this.msg=msg;
		}

		public String getCode() {
			return code;
		}

		@Override
		public String getMsg() {
			return msg;
		}
	}

	/**
	 * 使用状态 0：未使用 1：已使用
	 */
	public enum UseStatusEnum  implements BaseCodeEnum {
		USED("1","已使用"),
		UNUSED("0","未使用");
		private String code;
		private String msg;
		private UseStatusEnum(String code,String msg) {
			this.code = code;
			this.msg=msg;
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
	
	
	/**
	 * 语言定义
	 * */
	public enum LangEnum implements BaseCodeEnum {
		ZH("zh","简体中文"),
		EN("en","英文"),
		;
		private String code;
		private String msg;
		private LangEnum(String code,String msg) {
			this.code = code;
			this.msg=msg;
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

	/**
	 * 关联业务ID分类
	 */
	public enum BaseBusinessEnum implements BaseCodeEnum {
		ARTICLE_TYPE("1", "咨询"),
		COURSE_TYPE("3", "课程"),
		ACTIVITY_TYPE("4", "活动"),
		PRODUCT_WIKI_TYPE("5", "产品百科"),
		;
		private String code;
		private String msg;

		private BaseBusinessEnum(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}

		public static BaseBusinessEnum get(String code) {
			for (BaseBusinessEnum baseBusinessEnum : BaseBusinessEnum.values()) {
				if (StringUtils.equals(code, baseBusinessEnum.code)) {
					return baseBusinessEnum;
				}
			}
			throw new RuntimeException("无效的业务类型");
		}
	}

	/**
	 * 动作分类（点赞，浏览记录，分享...）
	 */
	public enum BaseActionEnum implements BaseCodeEnum {
		BROWSE("1", "浏览记录"),
		PRAISE("2", "点赞"),
		SHARE("3", "分享"),
		;

		private String code;
		private String msg;
		private BaseActionEnum(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public String getMsg() {
			return msg;
		}
	}

	@Getter
	public enum BaseSymbol{
		//点
		POINT("."),
		//斜杠
		SLASH("/"),
		//反斜杠
		BACKSLASH("\\"),
		//冒号
		COLON(":");

		private String symbol;

		private BaseSymbol(String symbol){
			this.symbol=symbol;
		}
	}

	@Getter
	public enum BaseProtocol{
		//http协议
		HTTP("http://"),
		//https协议
		HTTPS("https://");
		private String protocol;

		private BaseProtocol(String protocol){
			this.protocol=protocol;
		}
	}

	@Getter
	public enum BaseRequestMethod{
		//post
		POST("POST"),
		//put
		PUT("PUT"),
		//delete
		DELETE("DELETE"),
		//get
		GET("GET");
		private String method;

		private BaseRequestMethod(String method){
			this.method=method;
		}
	}


	/**
	 * 邀请码类型枚举
	 */
	public enum IRelBusiType implements BaseCodeEnum {
		UC_User_ID("1","用户类型"),

		;
		private String code;
		private String msg;
		private IRelBusiType(String code,String msg) {
			this.code=code;
			this.msg=msg;
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



}
