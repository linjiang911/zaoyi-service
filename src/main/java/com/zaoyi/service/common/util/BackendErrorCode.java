package com.zaoyi.service.common.util;

public enum BackendErrorCode implements ErrorCode {
	exception(6, null), //
	DATABASE_ERROR(5, "数据库错误"), //
	ACCOUNTORPWD_CANNOT_NULL(4,"账号或密码不能为空"),
	ACCOUNT_EXIST(3, "帐号已存在"), //
	ACCOUNT_DISABLE(2, "帐号被禁用"), //
	ACCOUNTORPWD_NOT_EXISTORERROR(1, "帐号不存或密码错误"); //

	private int code = 0;
	private String errorMessage = null;

	private BackendErrorCode(int code, String errorMessage) {
		this.code = code;
		this.errorMessage = errorMessage;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
