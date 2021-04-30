package com.atguigu.scw.project.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("step 3-project return info")
@ToString
@Data
public class ProjectReturnVo extends BaseVo{
	
	@ApiModelProperty(value="project token", required = true)
	private String projectToken;
	@ApiModelProperty(value = "return type：  0-virtual return   1-material return", required = true)
	private String type;
	@ApiModelProperty(value = "support money", required = true)
    private Integer supportmoney;
	@ApiModelProperty("return content")
    private String content;
	@ApiModelProperty(value="return count",required = true)
    private Integer count;
	@ApiModelProperty(value="quantity limit for single purchase",required = true)
    private Integer signalpurchase;
	@ApiModelProperty(value="purchase quantity",required = true)
    private Integer purchase;
	@ApiModelProperty(value="freight",required = true)
    private Integer freight;
	@ApiModelProperty(value = "invoice  0-no invoice  1-has invoice", required = true)
	private String invoice;
	@ApiModelProperty(value = "return days", required = true)
	private Integer rtndate;
}
