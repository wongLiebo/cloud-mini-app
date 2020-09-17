package com.mini.cloud.common.auth.handle;

public class AuthHandleFactory {

	
	
	public static AuthHandle getAuthHandle(String key) {
		if(key==null || "".equals(key)) {
			return null;
		}
		key = key.trim().toLowerCase();
		if(key.equals(AuthHandle.Anon)) {
			return AnonHandle.getInstall();
		}else if(key.equals(AuthHandle.Login)) {
			return LoginHandle.getInstall();
		}else if(key.equals(AuthHandle.Authc)) {
			return AuthcHandle.getInstall();
		}else {
			return null;
		}
	}
	
	
	
	
}
