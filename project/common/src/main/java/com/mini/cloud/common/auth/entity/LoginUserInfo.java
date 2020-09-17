package com.mini.cloud.common.auth.entity;

import com.mini.cloud.common.bean.DataMapEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class LoginUserInfo {


	@ApiModelProperty(value = "用户ID")
	private Integer id;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "公司")
	private String companyNo;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "头像")
	private String headImg;

	@ApiModelProperty(value = "用户角色")
	private Set<String> roles;

	@ApiModelProperty(value = "语言分类")
	private String langType;

	@ApiModelProperty(value = "IP地址")
	private String ip;

	@ApiModelProperty(value = "用户登陆标识")
	private String token;

	@ApiModelProperty(value = "其它信息")
	private DataMapEntity info;

	@ApiModelProperty(value = "岗位")
	private List<String> jobs;

	@ApiModelProperty(value = "用户状态")
	private String userState;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "电话")
	private String tel;

	@ApiModelProperty(value = "QQ")
	private String qq;

	@ApiModelProperty(value = "是否管理员")
	private String isManager;

	@ApiModelProperty(value = "部门ID")
	private Integer deptId;

	@ApiModelProperty(value = "是否部门负责人")
	private String isDeptManager;

	@ApiModelProperty(value = "数据权限分类(1全部部门 2自己 3指定部门)")
	private String dataAuthorityType;

	@ApiModelProperty(value = "数据权限范围")
	private String dataAuthorityScope;

	@ApiModelProperty(value = "数据权限")
	private List<String> dataScopes;

	public void addRoleCode(String roleCode) {
		if (StringUtils.isEmpty(roleCode)) {
			return;
		}
		if (this.roles == null) {
			this.roles = new HashSet<String>();
		}
		this.roles.add(roleCode);
	}

}
