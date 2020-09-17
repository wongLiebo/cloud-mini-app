package com.mini.cloud.app.vo.req;

import com.mini.cloud.common.bean.BasePageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 */
@Data
public class LogQueryReqParamsVo extends BasePageParam {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "请求方法名称")
    private String operMethod;

    @ApiModelProperty(value = "sessionid")
    private String sessionId;

    @ApiModelProperty(value = "操作开始时间 格式：yyyy-MM-dd HH:mm:ss")
    private String operTimeStart;

    @ApiModelProperty(value = "操作结束时间 格式：yyyy-MM-dd HH:mm:ss")
    private String operTimeEnd;


}
