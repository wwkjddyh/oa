package com.oa.platform.biz;

import com.oa.platform.common.Constants;
import com.oa.platform.service.VerifyCodeGen;
import com.oa.platform.entity.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码处理处理
 * @author Feng
 * @date 2020/03/11
 */
@Component
public class VerifyBiz extends BaseBiz {

    @Autowired
    private VerifyCodeGen verifyCodeGen;

    /**
     * 生成校验码
     * @param request 请求对象
     * @param response 响应对象
     */
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置长宽
            VerifyCode verifyCode = verifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            LOGGER.info(code);
            //将VerifyCode绑定session
            request.getSession().setAttribute(Constants.VERIFY_CODE, code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            LOGGER.info("", e);
        }
    }

}
