package com.mini.cloud.app.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PrizesVo {

    @ApiModelProperty(value = "券模板集合：tplIds")
    private List<String> tplIds;

    @ApiModelProperty(value = "userId")
    private String userId;
}
