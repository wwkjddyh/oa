package com.oa.platform.biz;

import com.oa.platform.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 地域信息业务处理
 */
@Component
public class AreaBiz extends BaseBiz {

    @Autowired
    private AreaService areaService;
}
