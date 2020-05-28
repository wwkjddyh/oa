package com.oa.platform.entity;

import java.io.Serializable;

public class TreeDict implements Serializable{

	private static final long serialVersionUID = 2397878213645003007L;

	//数据主键
	private String staticsId;
	//节点主键
	private String nodeId;
	//节点名称
	private String nodeName;
	//上级节点
	private String upperNode;
	//是否叶子
	private String isLeaf;
	//节点类型
	private String nodeType;
	//创建人
	private String createBy;
	//更新人
	private String updateBy;
	public String getStaticsId() {
		return staticsId;
	}
	public void setStaticsId(String staticsId) {
		this.staticsId = staticsId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getUpperNode() {
		return upperNode;
	}
	public void setUpperNode(String upperNode) {
		this.upperNode = upperNode;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}
