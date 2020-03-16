package com.oa.platform.service.impl;

import com.oa.platform.entity.PartyDues;
import com.oa.platform.service.PartyDuesService;
import org.springframework.stereotype.Service;

/**
 * 党费(缴纳)
 * @author jianbo.feng
 * @date 2020/03/16
 */
@Service
public class PartyDuesServiceImpl extends AbstractBaseService<PartyDues, String> implements PartyDuesService {

    PartyDuesServiceImpl() {
        super(PartyDues.class);
    }
}
