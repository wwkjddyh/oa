package com.oa.platform.biz;
/**
 * biz
 * @author 俞灶森
 *
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oa.platform.entity.TreeDict;
import com.oa.platform.service.TreeDictService;

@Component
public class TreeDictBiz extends BaseBiz{
	
	@Autowired
	private TreeDictService treeDictService;
	
	/**
	 * 根据类型获取树数据
	 * @param treeType
	 * @return
	 */
	public List<TreeDict> getTreeDictByType(String treeType) {
		
		return treeDictService.getTreeDictByType(treeType);
	}

}
