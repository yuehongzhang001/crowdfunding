package com.atguigu.scw.enums;

public enum UserExceptionEnum {
	
	LOGINACCT_EXIST(1,"Account already exists!"),
	EMAIL_EXIST(2,"Email already exists!"),
	LOGINACCT_LOCKED(3,"Account is locked!"),
	USER_SAVE_ERROR(4,"User save error!"), 
	USER_UNEXITS(5,"User not exists!"), 
	USER_PASSWORD_ERROR(6,"User password not correct!");
	
	private int code;
	private String msg;
	
	
	private UserExceptionEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	

}
