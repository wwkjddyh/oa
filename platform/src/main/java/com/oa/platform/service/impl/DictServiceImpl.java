package com.oa.platform.service.impl;

import com.oa.platform.entity.Dict;
import com.oa.platform.repository.DictRepository;
import com.oa.platform.service.DictService;
import com.oa.platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典
 * @author Feng
 * @date 2018/08/23
 */
@Service
public class DictServiceImpl extends AbstractBaseService<Dict, String> implements DictService {

    @Autowired
    private DictRepository dictRepository;

    public DictServiceImpl() {
        super(Dict.class);
    }

    @Transactional
    @Override
    public void updateDictFlagById(String dictId,Integer recordFlag) {
        dictId = StringUtil.trim(dictId);
        if(!"".equals(dictId) && recordFlag != null) {
            Dict dict = new Dict();
            dict.setDictId(dictId);
            dict.setRecordFlag(recordFlag);
            dictRepository.update(dict);
        }
    }
}
