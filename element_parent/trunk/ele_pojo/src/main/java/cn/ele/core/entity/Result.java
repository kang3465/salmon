package cn.ele.core.entity;

import java.io.Serializable;

public class Result implements Serializable{

	private static final long serialVersionUID = -8946453797496982517L;
	
	private Integer code;
	private String title;
	private String message;

	public Result() {
		super();
		this.code = -1;
		this.title="没有错误信息";
		this.message = "没有错误信息";
	}
	public Result(String message) {
		super();
		this.message = message;
	}
	public Result(String title, String message) {
		super();
		this.title = title;
		this.message = message;
	}public Result(Integer code,String title, String message) {
		super();
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

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
