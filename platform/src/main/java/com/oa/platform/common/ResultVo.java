package com.oa.platform.common;
/**
 * 返回封装
 * @author 俞灶森
 *
 */
public class ResultVo {

	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * 信息
	 */
	private String msg;

	/**
	 * 结果
	 */
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

	public ResultVo() {
	}

	public ResultVo(Integer code) {
		this.code = code;
	}

	public ResultVo(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultVo(Integer code, String msg, Object result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResultVo{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", result=" + result +
				'}';
	}
}
