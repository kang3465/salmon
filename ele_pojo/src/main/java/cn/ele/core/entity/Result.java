package cn.ele.core.entity;

import java.io.Serializable;

public class Result implements Serializable{

	private static final long serialVersionUID = -8946453797496982517L;
	
	private boolean success;
	private Integer code;
	private String title;
	private String message;

	public Result() {
		super();
		this.success = false;
		this.code = -1;
		this.title="没有错误信息";
		this.message = "没有错误信息";
	}
	public Result(boolean success) {
		super();
		this.success = success;
		this.title="没有错误信息";
		this.message = "没有错误信息";
	}
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public Result(boolean success,String title, String message) {
		super();
		this.success = success;
		this.title = title;
		this.message = message;
	}public Result(boolean success,Integer code,String title, String message) {
		super();
		this.success = success;
		this.code = code;
		this.title = title;
		this.message = message;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
