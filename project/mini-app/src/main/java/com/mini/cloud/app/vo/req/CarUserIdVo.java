package com.mini.cloud.app.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CarUserIdVo {

    @ApiModelProperty(value = "用户id")
    private String userId;

}
