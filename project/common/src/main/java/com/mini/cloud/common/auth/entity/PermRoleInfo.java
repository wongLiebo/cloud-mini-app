package com.mini.cloud.common.auth.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermRoleInfo {
	
	private String permUrl;
	
	private String permCode;
	
	private String roleCode;
	
	public PermRoleInfo(String roleCode,String permUrl) {
		this.roleCode=roleCode;
		this.permUrl=permUrl;
	}



	public PermRoleInfo(String roleCode,String permCode,String permUrl) {
		this.roleCode=roleCode;
		this.permCode=permCode;
		this.permUrl=permUrl;
	}

	public PermRoleInfo() {
	}
}
