package com.atguigu.scw.user.vo.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class UserRegistVo implements Serializable {
	@ApiModelProperty("mobile phone number")
	private String loginacct;
	@ApiModelProperty("password")
	private String userpswd;
	@ApiModelProperty("email")
	private String email;
	@ApiModelProperty("verification code")
	private String code;
	@ApiModelProperty("user type:0-person, 1-company")
	private String usertype;
}
