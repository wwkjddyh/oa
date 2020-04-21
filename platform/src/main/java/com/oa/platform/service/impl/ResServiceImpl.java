package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Res;
import com.oa.platform.entity.ResDl;
import com.oa.platform.repository.ResRepository;
import com.oa.platform.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源信息
 * @author jianbo.feng
 * @date 2020/03/14
 */
@Service
public class ResServiceImpl extends AbstractBaseService<Res, String> implements ResService {

    @Autowired
    private ResRepository resRepository;

    public ResServiceImpl() {
        super(Res.class);
    }

    @Override
    public void saveResDl(ResDl resDl) {
        resRepository.insertResDl(resDl);
    }

    @Override
    public void batchSaveResDl(List<ResDl> dlList) {
        resRepository.batchInsertResDl(dlList);
    }

    @Override
    public void updateResDl(ResDl resDl) {
        resRepository.updateResDl(resDl);
    }

    @Override
    public void deleteResDl(ResDl resDl) {
        resRepository.deleteResDl(resDl);
    }

    @Override
    public List<ResDl> findResDl(ResDl resDl) {
        return resRepository.findResDl(resDl);
    }

    @Override
    public PageInfo<ResDl> searchResDl(ResDl resDl, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(findResDl(resDl));
    }

	@Override
	public List<String> getOrgIdByUserId(String userId) {
		
		return resRepository.getOrgIdByUserId(userId);
	}
}
