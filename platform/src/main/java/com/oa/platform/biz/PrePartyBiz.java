package com.oa.platform.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 发展党员
 * @author 俞灶森
 *
 */

import com.oa.platform.common.ResultVo;
import com.oa.platform.entity.PrePartyMemeber;
import com.oa.platform.service.PrePartyService;
import com.oa.platform.util.StringUtil;
@Component
public class PrePartyBiz extends BaseBiz{
	
	@Autowired
	private PrePartyService prePartyService;
	
	public void changeStageByUserId(String userId, Integer stage) {
		prePartyService.changeStageByUserId(userId, stage);
		
	}
	
	public void deletePrePartyById(String userId) {
		prePartyService.deletePrePartyById(userId);
		
	}
	
	public List<PrePartyMemeber> getPrePartyList(String orgId, String userName, Integer stage) {
		return prePartyService.getPrePartyList(orgId, userName, stage);
	}

	public ResultVo save(PrePartyMemeber preParty,String userId) {
		if("".equals(StringUtil.trim(preParty.getUserName()))) {
			return getErroResultVo(2000, "发展党员姓名为空", null);
		}
		preParty.setUserId(StringUtil.getRandomUUID());
		preParty.setRecordFlag("1");
		preParty.setStage(1);
		preParty.setRecorderId(userId);
		prePartyService.save(preParty);
		return getSuccessResultVo(null);
	}
}
