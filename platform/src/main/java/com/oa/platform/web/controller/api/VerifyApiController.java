package com.oa.platform.web.controller.api;

import com.oa.platform.biz.VerifyBiz;
import com.oa.platform.common.Constants;
import com.oa.platform.util.StringUtil;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

    /**
     * 获得验证码图片
     * @param request 请求对象
     * @param response 返回对象
     */
    @GetMapping("/code")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        verifyBiz.verifyCode(request, response);
    }

    /**
     * 获得验证码
     * @param request 请求对象
     * @return
     */
    @GetMapping("/getCode")
    public Map<String, Object> getVerifyCode(HttpServletRequest request) {
        return verifyBiz.getVerifyCode(request);
    }

}
