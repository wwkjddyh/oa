package com.oa.platform.web.controller.api;

import com.oa.platform.biz.AreaBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限API接口
 * @author Feng
 * @date 2020/03/14
 */
@RestController
@RequestMapping("/api/area")
public class AreaApiController extends BaseController {

    @Autowired
    private AreaBiz areaBiz;
}
