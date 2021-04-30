package com.atguigu.scw.enums;

public enum ProjectReturnEnume {

	VIRTUAL((byte) 0, "virtual product"), REAL((byte) 1, "entity return");

	private byte code;
	private String type;

	private ProjectReturnEnume(byte code, String type) {
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
