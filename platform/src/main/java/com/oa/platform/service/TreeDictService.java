package com.oa.platform.service;

import java.util.List;

import com.oa.platform.entity.TreeDict;
/**
 * interface
 * @author 俞灶森
 *
 */
public interface TreeDictService extends BaseService<TreeDict, String>{

	List<TreeDict> getTreeDictByType(String treeType);

}
