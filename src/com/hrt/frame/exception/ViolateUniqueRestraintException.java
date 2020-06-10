package com.hrt.frame.exception;

/**
 * 类功能：违反唯一约束异常
 * 创建人：wwy
 * 创建日期：2012-12-5
 */
public class ViolateUniqueRestraintException extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	public ViolateUniqueRestraintException() {
		super();
	}
	
	public ViolateUniqueRestraintException(String message) {
		super(message);
	}

}
