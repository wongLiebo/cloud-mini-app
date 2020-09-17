package com.mini.cloud.common.bean;

import com.mini.cloud.common.constant.BaseReturnEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;



@Getter
@Setter
public class BaseResult{

	private static final long serialVersionUID = 1L;

	private String code;

	private DataMapEntity res=new DataMapEntity();

	public BaseResult() {

	}

	public  void addMsg(String msg){
		if(this.res==null){
			this.res=new DataMapEntity();
		}
		if(msg!=null && !"".equals(msg)){
			this.res.put("msg",msg);
		}
	}

	public void addData(Object data){
		if(this.res==null){
			this.res=new DataMapEntity();
		}
		if(data!=null){
			this.res.put("data",data);
		}
	}



//v1.0
//	private String msg;
//	private T data;



	public static  BaseResult error() {
		return error(BaseReturnEnum.SYSTEM_ERROR.getCode(), BaseReturnEnum.SYSTEM_ERROR.getMsg(), null);
	}

	public static  BaseResult error(String msg) {
		return error(BaseReturnEnum.SYSTEM_ERROR.getCode(), msg, null);
	}

	public static  BaseResult error(String code, String msg) {
		return error(code, msg, null);
	}

	public static  BaseResult error(String code, String msg, Object data) {
		//String lang= AuthContextUtils.getLang();
		BaseResult result = new BaseResult();
		result.setCode(code);
		result.addMsg(msg);
		result.addData(data);
//		v1.0
//		result.setMsg(msg);
//		result.setData(data);
		return result;
	}

	public static  BaseResult ok() {
		return ok(null);
	}

	public static  BaseResult ok(Object data) {
		BaseResult result = new BaseResult();
		result.setCode(BaseReturnEnum.SUCCESS.getCode());
		result.addMsg(BaseReturnEnum.SUCCESS.getMsg());
		result.addData(data);
//		v1.0
//		result.setMsg(BaseReturnEnum.SUCCESS.getMsg());
//		result.setData(data);
		return result;
	}

    public static BaseResult ok(Map<String, Object> data) {
		BaseResult result = new BaseResult();
		result.setCode(BaseReturnEnum.SUCCESS.getCode());
		result.addMsg(BaseReturnEnum.SUCCESS.getMsg());
		result.addData(data);
//		v1.0
//		BaseResult result = new BaseResult();
//      result.setCode(BaseReturnEnum.SUCCESS.getCode());
//      result.setMsg(BaseReturnEnum.SUCCESS.getMsg());
//      result.setData(data);
        return result;
    }


	public static BaseResult ok(String key, Object val) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(key, val);

		BaseResult result = new BaseResult();
		result.setCode(BaseReturnEnum.SUCCESS.getCode());
		result.addMsg(BaseReturnEnum.SUCCESS.getMsg());
		result.addData(data);

//      v1.0
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put(key, val);
//		BaseResult<Map<String, Object>> result = new BaseResult<>();
//		result.setCode(BaseReturnEnum.SUCCESS.getCode());
//		result.setMsg(BaseReturnEnum.SUCCESS.getMsg());
//		result.setData(data);
		return result;
	}


}

