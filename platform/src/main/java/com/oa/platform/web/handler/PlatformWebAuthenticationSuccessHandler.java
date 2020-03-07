package com.oa.platform.web.handler;

import com.alibaba.fastjson.JSONObject;
import com.oa.platform.entity.User;
import com.oa.platform.service.LogService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 平台授权成功处理
 * @author Feng
 * @date 2018/10/15
 */
@Component
public class PlatformWebAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LogService logService;

    @Autowired
    UserService userService;

    @Value(value="${platform.session-keys.user}")
    String userSessionKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        logger.info("PlatformWebAuthenticationSuccessHandler>>>>登录成功");

        /*
        if(authentication != null) {
            String name = StringUtil.trim(authentication.getName());
            if(!"".equals(name)) {
                Object principal = authentication.getPrincipal();
                if(principal != null) {
                    User user = (User) principal;
                    System.err.println("登录用户信息："+ JSONObject.toJSONString(user));
                }
                List<AuthRole> authRoleList = (List<AuthRole>)authentication.getAuthorities();
                System.err.println("登录用户权限信息："+ JSONObject.toJSONString(authRoleList));

                WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
                System.err.println("sessionId:"+details.getSessionId()+",remoteAddress:"+details.getRemoteAddress());
            }
        }*/

        if(authentication != null) {
            String name = StringUtil.trim(authentication.getName());
            if(!"".equals(name)) {
                Object principal = authentication.getPrincipal();
                if(principal != null) {
                    User user = (User) principal;
                    System.err.println("登录用户信息："+ JSONObject.toJSONString(user));
                    String ipAddr = "";
                    if(authentication.getDetails() != null) {
                        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
                        ipAddr = details.getRemoteAddress();
                        logger.info("---------------------访问地址："+ipAddr);
                        logger.info("---------------------SessionId："+details.getSessionId());
                    }
                    //保存登录信息
                    logService.saveLoginLog(user.getUserId(),ipAddr);
                }
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
