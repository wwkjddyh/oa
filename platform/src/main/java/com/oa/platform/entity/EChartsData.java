package com.oa.platform.entity;

import java.io.Serializable;
import java.util.List;
/**
 * echarts数据结果集
 * @author 俞灶森
 *
 */
public class EChartsData implements Serializable{
	//数据主题
	private String title;
	//数据选项卡
	private List<String> legend;
	//数据
	private List<String> axis;
	private List<Object> data;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAxis() {
		return axis;
	}
	public void setAxis(List<String> axis) {
		this.axis = axis;
	}
	public List<String> getLegend() {
		return legend;
	}
	public void setLegend(List<String> legend) {
		this.legend = legend;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	
	
}
