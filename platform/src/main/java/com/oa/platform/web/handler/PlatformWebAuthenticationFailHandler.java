package com.oa.platform.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 平台授权异常处理
 * @author Feng
 * @date 2018/10/15
 */
@Component
public class PlatformWebAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("PlatformWebAuthenticationFailHandler>>>>登录失败");
        super.onAuthenticationFailure(request, response, exception);
    }
}
