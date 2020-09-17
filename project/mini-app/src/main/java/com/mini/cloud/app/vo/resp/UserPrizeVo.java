package com.mini.cloud.app.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户领券详情vo
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户领券详情vo")
public class UserPrizeVo implements Serializable {

    @ApiModelProperty(value = "用户ID", position = 1)
    private String userId;

    @ApiModelProperty(value = "券id", position = 2)
    private String prizeId;

    @ApiModelProperty(value = "券模板id", position = 3)
    private String tplId;

    @ApiModelProperty(value = "券模板名称", position = 4)
    private String tplName;

    @ApiModelProperty(value = "发券类型：“2”为基于用户信息识别", position = 5)
    private String recognitionType;

    @ApiModelProperty(value = "备注", position = 6)
    private String remark;

    @ApiModelProperty(value = "创建时间", position = 7)
    private String createTime;


}
