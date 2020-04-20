package com.oa.platform.vo;

import java.io.Serializable;
import java.util.List;


/**
 * 自定义字段
 * @author Feng
 * @date 2017/11/30
 *
 */
public class MyFieldVo implements Serializable {

	private static final long serialVersionUID = 4556169044308306707L;

	/*对应sheet名称(或表名)*/
	private String name;
	
	/*中文名称*/
	private String cnName;
	
	/*标题行信息*/
	private List<MyField> myFields;
	
	/*数据信息*/
	private List<List<MyField>> dataRows;
	
	public MyFieldVo() {
	}
	
	public MyFieldVo(List<MyField> myFields) {
		super();
		this.myFields = myFields;
	}

	public MyFieldVo(String name, List<MyField> myFields) {
		super();
		this.name = name;
		this.myFields = myFields;
	}

	public MyFieldVo(String name,String cnName, List<MyField> myFields) {
		super();
		this.name = name;
		this.cnName = cnName;
		this.myFields = myFields;
	}
	
	public MyFieldVo(String name,String cnName, List<MyField> myFields, List<List<MyField>> dataRows) {
		super();
		this.name = name;
		this.cnName = cnName;
		this.myFields = myFields;
		this.dataRows = dataRows;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public List<MyField> getMyFields() {
		return myFields;
	}

	public void setMyFields(List<MyField> myFields) {
		this.myFields = myFields;
	}

	public List<List<MyField>> getDataRows() {
		return dataRows;
	}

	public void setDataRows(List<List<MyField>> dataRows) {
		this.dataRows = dataRows;
	}

	@Override
	public String toString() {
		return "MyFieldVo{" +
				"name='" + name + '\'' +
				", cnName='" + cnName + '\'' +
				", myFields=" + myFields +
				", dataRows=" + dataRows +
				'}';
	}
}
