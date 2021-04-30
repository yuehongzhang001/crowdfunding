package com.atguigu.scw.user.vo.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class UserRespVo implements Serializable {
	
	@ApiModelProperty("accessToken, must be taken every time to access")
	private String accessToken;
	@ApiModelProperty("login acctount")
	private String loginacct;
	@ApiModelProperty("email")
	private String email;
	@ApiModelProperty("authentication status")
	private String authstatus;
	@ApiModelProperty("usertype:0-person, 1-company")
	private String usertype;
	@ApiModelProperty("realname")
	private String realname;
	@ApiModelProperty("card number")
	private String cardnum;
	@ApiModelProperty("card number")
	private String accttype;
}
