package com.oa.platform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.platform.entity.PrePartyMemeber;
import com.oa.platform.repository.PrePartyRepository;
import com.oa.platform.service.PrePartyService;
/**
 * 发展党员
 * @author 俞灶森
 *
 */
@Service
public class PrePartyServiceImpl extends AbstractBaseService<PrePartyMemeber, String>  implements PrePartyService {
	
	public PrePartyServiceImpl() {
		super(PrePartyMemeber.class);
	}
	
	@Autowired
	private PrePartyRepository prePartyRepository;
	
	@Override
	public void changeStageByUserId(String userId, Integer stage) {
		prePartyRepository.changeStageByUserId(userId, stage);
		
	}

	@Override
	public void deletePrePartyById(String userId) {
		prePartyRepository.deletePrePartyById(userId);
		
	}

	@Override
	public List<PrePartyMemeber> getPrePartyList(String orgId, String userName, Integer stage) {
		return prePartyRepository.getPrePartyList(orgId, userName, stage);
	}
}
