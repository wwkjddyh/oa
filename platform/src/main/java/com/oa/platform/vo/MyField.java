package com.oa.platform.vo;

import java.io.Serializable;

/**
 * 自定义字段
 * @author Feng
 * @create 2017/11/30
 */
public class MyField implements Serializable {

	private static final long serialVersionUID = 3402701682678548242L;
	
	/**
	 * 类型：String
	 */
	public final static String TYPE_STRING = "STRING";
	
	/**
	 * 类型：Boolean
	 */
	public final static String TYPE_BOOLEAN = "BOOLEAN";
	
	/**
	 * 类型：Integer
	 */
	public final static String TYPE_INTEGER = "INTEGER";
	
	/**
	 * 类型：FORMULA
	 */
	public final static String TYPE_FORMULA = "FORMULA";
	
	/**
	 * 字段名(英文)
	 */
	private String fieldName;
	
	/**
	 * 类型
	 */
	private String typeName;
	
	/**
	 * 长度
	 */
	private int length;
	
	/**
	 * 是否为null
	 */
	private boolean isNull;
	
	/**
	 * 中文名称
	 */
	private String cnName;
	
	/**
	 * 描述
	 */
	private String fieldDesc;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public static String getTypeString() {
		return TYPE_STRING;
	}

	public MyField(String fieldName, String typeName) {
		super();
		this.fieldName = fieldName;
		this.typeName = typeName;
	}
	
	public MyField(String fieldName, String typeName, int length) {
		super();
		this.fieldName = fieldName;
		this.typeName = typeName;
		this.length = length;
	}

	public MyField(String fieldName, String typeName, int length, boolean isNull) {
		super();
		this.fieldName = fieldName;
		this.typeName = typeName;
		this.length = length;
		this.isNull = isNull;
	}
	
	public MyField(String fieldName, String typeName, int length, boolean isNull, String cnName, String fieldDesc) {
		super();
		this.fieldName = fieldName;
		this.typeName = typeName;
		this.length = length;
		this.isNull = isNull;
		this.cnName = cnName;
		this.fieldDesc = fieldDesc;
	}

	public MyField() {
	}

	@Override
	public String toString() {
		return "MyField{" +
				"fieldName='" + fieldName + '\'' +
				", typeName='" + typeName + '\'' +
				", length=" + length +
				", isNull=" + isNull +
				", cnName='" + cnName + '\'' +
				", fieldDesc='" + fieldDesc + '\'' +
				'}';
	}
}
