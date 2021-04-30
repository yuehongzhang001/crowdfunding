package com.atguigu.scw.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel()
public class User {

	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("username")
	private String uname;
	
}
