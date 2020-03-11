package com.oa.platform.common;
/**
 * 返回封装
 * @author 俞灶森
 *
 */
public class ResultVo {
	
	//状态码
	private Integer code;
	//信息
	private String msg;
	//结果
	private Object result;

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
