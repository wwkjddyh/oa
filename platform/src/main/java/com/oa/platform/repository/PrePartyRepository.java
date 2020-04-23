package com.oa.platform.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oa.platform.entity.PrePartyMemeber;
/**
 * 发展党员
 * @author 俞灶森
 *
 */
@Repository
public interface PrePartyRepository extends BaseRepository<PrePartyMemeber, String>{
	/**
	 * 发展党员状态改变
	 * @param userId
	 * @param stage
	 */
	void changeStageByUserId(@Param("userId") String userId,@Param("stage") Integer stage);
	/**
	 * 发展党员删除
	 * @param userId
	 */
	void deletePrePartyById(@Param("userId") String userId);
	/**
	 * 发展党员查询
	 * @param orgId
	 * @param userName
	 * @param stage
	 * @return
	 */
	List<PrePartyMemeber> getPrePartyList(@Param("orgIds") List<String> orgIds,@Param("userName") String userName,@Param("stage") Integer stage);
	void deletefzdyByOrg(@Param("orgId") String orgId);
}
