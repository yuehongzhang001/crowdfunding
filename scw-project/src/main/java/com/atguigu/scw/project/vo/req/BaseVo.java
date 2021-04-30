package com.atguigu.scw.project.vo.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@ApiModel("step-1 info")
@Data
public class BaseVo implements Serializable {

	@ApiModelProperty("accessToken to identify user")
	private String accessToken;
}
