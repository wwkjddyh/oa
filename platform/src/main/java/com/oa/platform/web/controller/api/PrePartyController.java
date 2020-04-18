package com.oa.platform.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.platform.biz.PrePartyBiz;
import com.oa.platform.common.ResultVo;
import com.oa.platform.entity.PrePartyMemeber;
import com.oa.platform.web.controller.BaseController;
/**
 * 发展党员
 * @author 俞灶森
 *
 */
@RestController
@RequestMapping("/api/preParty")
public class PrePartyController extends BaseController {
	
	@Autowired
	private PrePartyBiz prePartyBiz;
	
	/**
	 * 发展党员阶段变更
	 * @param userId
	 * @param stage
	 * @return
	 */
	@GetMapping("changeStageByUserId")
	public ResultVo changeStageByUserId(String userId,Integer stage) {
		prePartyBiz.changeStageByUserId(userId, stage);
		return getSuccessResultVo(null);
	}
	/**
	 * 发展党员删除
	 * @param userId
	 * @return
	 */
	@GetMapping("deletePrePartyById")
	public ResultVo deletePrePartyById(String userId) {
		prePartyBiz.deletePrePartyById(userId);
		return getSuccessResultVo(null);
	}
	/**
	 * 发展党员新增
	 * @param preParty
	 * @return
	 */
	@PostMapping("save")
	public ResultVo save(PrePartyMemeber preParty) {
		String userId = this.getUserIdOfSecurity();
		return prePartyBiz.save(preParty,userId);
	}
	
	@GetMapping("getPrePartyList")
	public ResultVo getPrePartyList(String orgId,String userName,Integer stage) {
		List<PrePartyMemeber> result = prePartyBiz.getPrePartyList(orgId, userName, stage);
		return getSuccessResultVo(result);
	}
}
