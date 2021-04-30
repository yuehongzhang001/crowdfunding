package com.atguigu.scw.enums;

/**
 * 项目状态
 */
public enum ProjectStatusEnume {
	
	DRAFT((byte)0,"draft"),
	SUBMIT_AUTH((byte)1,"submit for authentication"),
	AUTHING((byte)2,"authenticating"),
	AUTHED((byte)3,"authentication approved"),
	AUTHFAIL((byte)4,"authentication failed"),
	STARTING((byte)5,"starting funding"),//新增众筹项目一些状态
	SUCCESS((byte)6,"funding success"),
	FINISHED((byte)7,"funding finished"),
	FAIL((byte)8,"funding failed");
	
	private byte code;
	private String status;
	public byte getCode() {
		return code;
	}
	public void setCode(byte code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private ProjectStatusEnume(byte code, String status) {
		this.code = code;
		this.status = status;
	}
	

}
