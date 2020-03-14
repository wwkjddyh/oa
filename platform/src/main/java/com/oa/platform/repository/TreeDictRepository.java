package com.oa.platform.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oa.platform.entity.TreeDict;
/**
 * dao
 * 
 * @author 俞灶森
 *
 */
@Repository
public interface TreeDictRepository extends BaseRepository<TreeDict, String>{

	List<TreeDict> getTreeDictByType(@Param("treeType") String treeType);

}
