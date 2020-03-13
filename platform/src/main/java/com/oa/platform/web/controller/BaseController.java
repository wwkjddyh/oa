package com.oa.platform.web.controller;

import com.google.common.collect.Lists;
import com.oa.platform.biz.BaseBiz;
import com.oa.platform.common.Constants;
import com.oa.platform.common.ResultVo;
import com.oa.platform.common.StatusCode;
import com.oa.platform.entity.Module;
import com.oa.platform.entity.Role;
import com.oa.platform.entity.User;
import com.oa.platform.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 基础类
 * @author Feng
 * @date 2019/03/01
 */
public abstract class BaseController {

    /**
     * 默认页码：1
     */
    public static final int PAGE_NUM = BaseBiz.PAGE_NUM;

    /**
     * 默认每页记录数：10
     */
    public static final int PAGE_SIZE = BaseBiz.PAGE_SIZE;

    /**
     * 默认页码：1 (字符串)
     */
    public static final String PAGE_NUM_STR = BaseBiz.PAGE_NUM + "";

    /**
     * 默认每页记录数：10 (字符串)
     */
    public static final String PAGE_SIZE_STR = BaseBiz.PAGE_SIZE + "";

    protected final static Logger LOGGER = LoggerFactory.getLogger("BaseController");

    /**
     * 获得HttpServletRequest
     * @return
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获得HttpServletResponse
     * @return
     */
    public HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }

    /**
     * 获得HttpSession
     * @return
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取验证信息
     * @return
     */
    public Authentication getAuthentication() {
        HttpSession session = getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute(Constants.SPRING_SECURITY_SESSION_KEY);
        return securityContext == null ? null : securityContext.getAuthentication();
    }

    /**
     * 从Security中获取登录用户信息
     * @return
     */
    public User getUserOfSecurity() {
        User user = null;
        Authentication authentication = getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null) {
                user = (User) principal;
            }
        }
        return user;
    }

    /**
     * 从Security中获取登录用户ID
     * @return
     */
    public String getUserIdOfSecurity() {
        String userId = "";
        User user = this.getUserOfSecurity();
        if(user != null) {
            userId = StringUtil.trim(user.getUserId());
        }
        return userId;
    }

    /**
     * 从Security中获取登录用户权限信息
     */
    public List<Role> getAuthorities() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : (List<Role>) authentication.getAuthorities();
    }

    /**
     * 获取用户所拥有的所有模块
     * @return
     */
    public List<Module> getModules() {
        List<Module> modules = Lists.newArrayList();
        User user = this.getUserOfSecurity();
        if (user != null) {
            modules = user.getModules();
        }
        return modules;
    }

    /**
     * 从Security中获取客户端访问IP地址及SessionId
     * @return
     */
    public WebAuthenticationDetails getWebAuthenticationDetails() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : (WebAuthenticationDetails) authentication.getDetails();
    }

    /**
     * 获得SessionID
     * @return
     */
    public String getSessionId() {
        WebAuthenticationDetails details = getWebAuthenticationDetails();
        return details == null ? "" : details.getSessionId();
    }

    /**
     * 获得RemoteAddress
     * @return
     */
    public String remoteAddress() {
        WebAuthenticationDetails details = getWebAuthenticationDetails();
        return details == null ? "" : details.getRemoteAddress();
    }

    /**
     * 从Security中获取登录用户名
     * @return
     */
    public String getUserNameOfSecurity() {
        Authentication authentication = getAuthentication();
        return authentication == null ? "" : StringUtil.trim(authentication.getName());
    }

    /**
     * 是否验证(读取Security中的信息)
     * @return
     */
    public boolean isLogonOfSecurity() {
        boolean isLogon = false;
        String userName = this.getUserNameOfSecurity();
        if(!"".equals(userName)) {
            isLogon = true;
        }
        return isLogon;
    }

    /**
     * 根据请求参数名获得值
     * @param paramName 参数名
     * @return 若paramName为null或空字符串，则返回""
     */
    public String getParameter(String paramName) {
        return getParameter(paramName, "");
    }

    /**
     * 根据请求参数名获得值
     * @param paramName 参数名
     * @param defVal 默认值
     * @return 若paramName为null或空字符串，则返回默认字符串
     */
    public String getParameter(String paramName, String defVal) {
        return getParameter(this.getRequest(),paramName,defVal);
    }

    /**
     * 根据请求参数名获取值
     * @param request 若为null，则直接使用函数getRequest获取
     * @param paramName 参数名
     * @return 若paramName为null或空字符串，则返回""
     */
    public String getParameter(HttpServletRequest request, String paramName) {
        return getParameter(request,paramName,"");
    }

    /**
     * 根据请求参数名获取值
     * @param request 若为null，则直接使用函数getRequest获取
     * @param paramName 参数名
     * @param defVal 默认值
     * @return 若paramName为null或空字符串，则返回默认字符串
     */
    public String getParameter(HttpServletRequest request, String paramName, String defVal) {
        String val = "";
        paramName = StringUtil.trim(paramName);
        request = request == null ? this.getRequest() : request;
        if(!"".equals(paramName)) {
            val = StringUtil.trim(request.getParameter(paramName),defVal);
        }
        return val;
    }

    /**
     * 设置SpringSecuritySession
     * @param authenticationManager 授权管理
     * @param request HttpServletRequest对象
     * @param username 用户名
     * @param password 用户密码
     */
    public void setSpringSecuritySession(AuthenticationManager authenticationManager,
                                         HttpServletRequest request, String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.SPRING_SECURITY_SESSION_KEY, securityContext);
    }
    /**
     * 获取成功返回
     * @param result
     * @return
     */
    public ResultVo getSuccessResultVo(Object result) {
    	ResultVo resultVo = new ResultVo();
    	resultVo.setCode(StatusCode.SUCCESS);
    	resultVo.setMsg(Constants.STR_SUCCESS);
    	resultVo.setResult(result);
    	return resultVo;
    }
    /**
     * 获取错误返回
     * @param code
     * @param msg
     * @param result
     * @return
     */
    public ResultVo getErroResultVo(Integer code,String msg,Object result) {
    	ResultVo resultVo = new ResultVo();
    	resultVo.setCode(code);
    	resultVo.setMsg(msg);
    	resultVo.setResult(result);
    	return resultVo;
    }
}
