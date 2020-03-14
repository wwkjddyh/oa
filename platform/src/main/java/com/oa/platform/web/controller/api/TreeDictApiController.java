package com.oa.platform.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.platform.biz.TreeDictBiz;
import com.oa.platform.common.ResultVo;
import com.oa.platform.entity.TreeDict;
import com.oa.platform.web.controller.BaseController;
/**
 * 树型数据字典通用类
 * @author 俞灶森
 *
 */
@RestController
@RequestMapping("/api/treeDict")
public class TreeDictApiController extends BaseController{
	
	@Autowired
	private TreeDictBiz treeDictBiz;
	/**
	 * 获取树字典集合
	 * @param treeType
	 * @return
	 */
	@GetMapping("getTreeDictByType")
	public ResultVo getTreeDictByType(String treeType) {
		List<TreeDict> result = treeDictBiz.getTreeDictByType(treeType);
		return getSuccessResultVo(result);
	}
}
