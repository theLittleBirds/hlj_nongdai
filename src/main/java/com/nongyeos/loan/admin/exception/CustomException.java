package com.nongyeos.loan.admin.exception;

import com.nongyeos.loan.admin.errorcode.ErrorEnum;
/**
 * 
 * 自定义异常类用于获取枚举中的异常类型
 * @author ThinkPad
 *
 */
public class CustomException extends RuntimeException{
	private Integer code;
	
	public CustomException(ErrorEnum resultEnum) {
	    super(resultEnum.getMsg());
	    this.code = resultEnum.getCode();
	}
	
	public Integer getCode() {
	    return code;
	}
	
	public void setCode(Integer code) {
	    this.code = code;
	}
}
