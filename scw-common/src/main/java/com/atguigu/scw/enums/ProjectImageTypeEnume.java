package com.atguigu.scw.enums;

public enum ProjectImageTypeEnume {
	HEADER_IMAGE((byte) 0,"header image"),
	DETAIL_IMAGE((byte) 1,"header image");
	
	private byte code;
	private String type;
	private ProjectImageTypeEnume(byte code, String type) {
		this.code = code;
		this.type = type;
	}
	public byte getCode() {
		return code;
	}
	public void setCode(byte code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
