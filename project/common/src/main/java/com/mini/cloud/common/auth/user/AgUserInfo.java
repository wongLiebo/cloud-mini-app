package com.mini.cloud.common.auth.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgUserInfo {

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "公司编号")
    private String companyNo;

//    @ApiModelProperty(value = "用户一级分类")
//    private String userTypeOne;
//
//    @ApiModelProperty(value = "用户二级分类")
//    private String userTypeTwo;

    @ApiModelProperty(value = "用户名")
    private String userName;


    @ApiModelProperty(value = "昵称")
    private String nickName;


    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "推荐人编号")
    private String referralCode;

    @ApiModelProperty(value = "推荐人用户ID")
    private String referralUserId;

    @ApiModelProperty(value = "分享码")
    private String shareCode;

    @ApiModelProperty(value = "实名认证状态 0:新用户 1:实名认证完成 2：实名认证且照片完成 3：实名认证且照片完成且答题通过 4：全部完成且银行卡已添加 -1.认证失败")
    private String status;

    @ApiModelProperty(value = "执业认证状态 0未认证 1已认证 2已认证未确认 -1认证失败")
    private String practiceStatus;

    @ApiModelProperty(value = "执业认证类型")
    private String practiceType;

    @ApiModelProperty(value = "执业编号")
    private String practiceNumber;

    @ApiModelProperty(value = "用户登陆标识")
    private String token;

    @ApiModelProperty(value = "统一身份标识userId")
    private String userId;

    @Override
    public String toString() {
        return "AgUserInfo{" +
                "id='" + id + '\'' +
                ", companyNo='" + companyNo + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", headImg='" + headImg + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", referralUserId='" + referralUserId + '\'' +
                ", shareCode='" + shareCode + '\'' +
                ", status='" + status + '\'' +
                ", practiceStatus='" + practiceStatus + '\'' +
                ", practiceType='" + practiceType + '\'' +
                ", practiceNumber='" + practiceNumber + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
