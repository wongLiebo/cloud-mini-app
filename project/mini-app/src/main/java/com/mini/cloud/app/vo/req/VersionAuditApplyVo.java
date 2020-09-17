package com.mini.cloud.app.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VersionAuditApplyVo {

    @ApiModelProperty(value = "版本号",required = true)
    private String version;

    @ApiModelProperty(value = "客服电话(不填默认为 13110101010)")
    private String servicePhone;

    @ApiModelProperty(value = "版本描述 (有默认值，推荐填写真实版本描述)")
    private String versionDesc;

    @ApiModelProperty(value = "小程序备注;最多500字符 (不填默认为 底盘号:123456 )")
    private String memo;





}
