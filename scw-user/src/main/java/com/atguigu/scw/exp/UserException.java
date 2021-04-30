package com.atguigu.scw.exp;

import com.atguigu.scw.enums.UserExceptionEnum;

public class UserException extends RuntimeException{

	public UserException(UserExceptionEnum userSaveError) {
		super(userSaveError.getMsg());
	}

	
}
