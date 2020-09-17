package com.mini.cloud.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BasePageParam /*extends BaseParam*/{

	@ApiModelProperty(value = "页码", example="1")
	private int page = 1;
	
	@ApiModelProperty(value = "每页记录数", example="10")
	private int rows = 10;
	
}
