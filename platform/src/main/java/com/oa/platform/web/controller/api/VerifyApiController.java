package com.oa.platform.web.controller.api;

import com.oa.platform.biz.VerifyBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验码接口
 * @author Feng
 * @date 2020/03/11
 */
@RestController
@RequestMapping("/api/verify")
public class VerifyApiController extends BaseController {

    @Autowired
    private VerifyBiz verifyBiz;

    @GetMapping("/code")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        verifyBiz.verifyCode(request, response);
    }

}
