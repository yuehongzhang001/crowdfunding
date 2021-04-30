package com.atguigu.scw.project.vo.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("step-2 info")
@ToString
@Data
public class ProjectBaseVo extends BaseVo{
	
	@ApiModelProperty("token to identify project")
	private String projectToken;//project tmp token
	@ApiModelProperty("project type")
	private List<Integer> typeids;//project type ids
	@ApiModelProperty("tag list")
	private List<Integer> tagids;//project tag ids
	@ApiModelProperty("project name")
	private String name;
	@ApiModelProperty("simple introduction")
	private String remark;
	@ApiModelProperty("money")
	private Long money;
	@ApiModelProperty("days")
	private Integer day;
	@ApiModelProperty("header image")
	private String headerImage;
	@ApiModelProperty("detail image list")
	private List<String> detailImages;
}
