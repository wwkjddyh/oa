package com.oa.platform.service.impl;

import com.oa.platform.entity.Area;
import com.oa.platform.service.AreaService;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl extends AbstractBaseService<Area, String> implements AreaService {

    public AreaServiceImpl() {
        super(Area.class);
    }
}
