package com.oa.platform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.platform.entity.TreeDict;
import com.oa.platform.repository.TreeDictRepository;
import com.oa.platform.service.TreeDictService;
/**
 * 树通用service
 * @author 俞灶森
 *
 */
@Service
public class TreeDictServiceImpl extends AbstractBaseService<TreeDict, String> implements TreeDictService {

	public TreeDictServiceImpl() {
		super(TreeDict.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@Autowired
	private TreeDictRepository treeDictRepository;
	
	@Override
	public List<TreeDict> getTreeDictByType(String treeType) {
		return treeDictRepository.getTreeDictByType(treeType);
	}

	

}
