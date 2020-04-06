package com.oa.platform.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.oa.platform.common.Constants;
import com.oa.platform.common.StatusCode;
import com.oa.platform.entity.User;
import com.oa.platform.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * 平台Web拦截器
 * @author Feng
 * @date 2018/09/11
 */
public class PlatformWebHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PlatformWebHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("---------------------开始进入请求地址拦截----------------------------");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
//        System.err.println("accept:"+request.getHeader("accept"));

        Map<String,Object> unLogon = StringUtil.getResultVo(StatusCode.UNAUTHORIZED,"UNAUTHORIZED","");
        String unLogonMsg = JSONObject.toJSONString(unLogon);
//        logger.info("sessionId=>" + request.getSession().getId());
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute(Constants.SPRING_SECURITY_SESSION_KEY);
        boolean isLogon = false;
        if(securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if(authentication != null) {
                String userName = authentication.getName();
                if(!"".equals(userName)) {
                    isLogon = true;
                }
            }
        }

//        Enumeration<String> headers = request.getHeaderNames();
//        while (headers.hasMoreElements()) {
//            String headerName = StringUtil.trim(headers.nextElement());
//            String headerValue = "".equals(headerName) ? "" : request.getHeader(headerName);
//            System.err.println("header-name:"+headers.nextElement()+",value:"+headerValue);
//        }

        String requestUri = request.getRequestURI();
//        logger.info("PlatformWebHandlerInterceptor.preHandle()>>>requestUri:"+requestUri+"\n");
        //Rest请求验证
        if(requestUri.contains("/api/")) {
            if(!isLogon) {
                print(response, unLogonMsg);
//                response.sendRedirect("/");
                return false;
            }
        }

        String xRequestedWith = request.getHeader("X-Requested-With") == null ? "" : request.getHeader("x-requested-with");
        //Ajax请求验证
        if(xRequestedWith != null && xRequestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            //如果是ajax请求响应头会有x-requested-with
            if(!isLogon) {
                print(response, unLogonMsg);
//                response.sendRedirect("/");
                return false;
            }
        }

        if(requestUri.contains("/error")) {
            response.sendRedirect("/");
            return false;
        }

        /*
        //当请求去的是登陆页面就直接放行
        if(request.getRequestURI().contains("login")) {
            return true;
        }*/

        //进行IP是否黑名单验证、登录验证等等
//        if(request.getSession().getAttribute("user") != null) {
//            logger.info("用户已登录");
//            return true;
//        }
//        else {
//            PrintWriter printWriter = response.getWriter();
//            Map<String,Object> info = StringUtil.getResultVo(StatusCode.NOT_FOUNED,"",null);
//            printWriter.write(JSON.toJSONString(info));
//            return false;
//        }
        //验证IP地址访问是否超过次数
        String ip = StringUtil.trim(getIpAddr(request));
//        logger.info("---------------------IP地址为："+ip);
        if(!"".equals(ip)) {

            if(securityContext == null) {
//                logger.info("---------------------securityContext信息为空");
//                print(response,unLogonMsg);
//                return false;
            }
            else {
                Authentication authentication = securityContext.getAuthentication();
                if(authentication != null) {
                    String username = StringUtil.trim(authentication.getName());
//                    logger.info("---------------------username:"+username);

                    /**
                     * 请登录
                     */
//                    if("".equals(username)) {
//                        print(response,unLogonMsg);
//                        return false;
//                    }

                    if(authentication.getCredentials() != null) {
                        //未加密密码
//                       logger.info("---------------------未加密密码:"+authentication.getCredentials());
                    }


                    if(authentication.getDetails() != null) {
                        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
//                        logger.info("---------------------访问地址："+details.getRemoteAddress());
//                        logger.info("---------------------SessionId："+details.getSessionId());
                    }

                    // 获得当前用户所拥有的权限
                    List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();


                    Object principal = authentication.getPrincipal();
                    if(principal != null) {
                        User user = (User) principal;
//                        logger.info("---------------------用户信息为username:'"+ JSONObject.toJSONString(user) +"'");
                    }
                }
            }
            //首先IP地址是否正确
//            boolean matches = IPWhiteUtil.checkLoginIP(ip,"1.0.0.1-255.255.255.255");
//            if(matches) {
//                logger.info("---------------------IP地址合规");
//                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                if(principal != null) {
//                    UserDetails userDetails = (UserDetails) principal;
//                    logger.info("---------------------用户信息为username:'"+userDetails.getUsername()+"'");
//                }
//                else {
//                    logger.info("---------------------用户信息为空");
//                }
//            }
//            else {
//                logger.info("---------------------IP地址不合规");
//            }
        }

        return true;
    }

    /**
     * 打印json信息
     * @param response
     */
    public void print(HttpServletResponse response, String json) {
        try {
            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();
        }
        catch(Exception e) {
//            logger.error(".....打印json信息异常",e);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        logger.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
//        logger.info("---------------视图渲染之后的操作-------------------------0");
    }

    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
//                        e.printStackTrace();
                        logger.error(e.getMessage(),e);
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
}
