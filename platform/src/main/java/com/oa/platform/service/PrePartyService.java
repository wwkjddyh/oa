package com.oa.platform.service;

import java.util.List;


import com.oa.platform.entity.PrePartyMemeber;

public interface PrePartyService extends BaseService<PrePartyMemeber, String> {
	/**
	 * 发展党员状态改变
	 * @param userId
	 * @param stage
	 */
	void changeStageByUserId(String userId,Integer stage);
	/**
	 * 发展党员删除
	 * @param userId
	 */
	void deletePrePartyById(String userId);
	/**
	 * 发展党员查询
	 * @param orgId
	 * @param userName
	 * @param stage
	 * @return
	 */
	List<PrePartyMemeber> getPrePartyList(List<String> orgIds,String userName,Integer stage);
}
