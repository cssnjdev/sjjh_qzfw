package com.cwks.bizcore.sjjh.core.exception;


/**
 * 核心层业务处理发生错误
 * <p>Title: ServiceProcessException.java</p>
 * <p>Description: 核心层业务处理发生错误</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
public class ServiceProcessException extends RuntimeException{

	private static final long serialVersionUID = -4220401995272329845L;
	public ServiceProcessException() {
		super();
	}

	public ServiceProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceProcessException(String message) {
		super(message);
	}

	public ServiceProcessException(Throwable cause) {
		super(cause);
	}
}
