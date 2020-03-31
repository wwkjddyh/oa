package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oa.platform.common.Constants;
import com.oa.platform.common.ResultVo;
import com.oa.platform.common.StatusCode;
import com.oa.platform.entity.Module;
import com.oa.platform.entity.Role;
import com.oa.platform.entity.User;
import com.oa.platform.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务抽象类
 * @author Feng
 * @date 2019/03/01
 */
public abstract class BaseBiz {

    /**
     * 默认页码：1
     */
    public static final int PAGE_NUM = 1;

    /**
     * 默认每页记录数：10
     */
    public static final int PAGE_SIZE = 10;

    protected final static Logger LOGGER = LoggerFactory.getLogger(BaseBiz.class);

    public Map<String,Object> ret = null;

    /**
     * 获得(列表)单一元素
     * @param list
     * @return
     */
    public Object getSingleElement(List<?> list) {
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获得(列表)单一元素返回信息
     * @param list
     * @return
     */
    public Map<String,Object> getSingleInfo(List<?> list) {
        Object ret = this.getSingleElement(list);
        if(ret == null) {
            return this.getParamErrorVo();
        }
        else {
            return this.getSuccessVo("", ret);
        }
    }

    /**
     * 获得分页信息
     * @param pageInfo
     * @return
     */
    public Map<String,Object> getPageInfo(PageInfo pageInfo) {
        if(pageInfo == null) {
            return getErrorVo();
        }
        else {
            Map<String,Object> data = new HashMap<>();
            data.put(Constants.PAGE_INFO_TOTAL,pageInfo.getTotal());
            data.put(Constants.PAGE_INFO_LIST,pageInfo.getList());
            data.put(Constants.PAGE_INFO_PAGE_NUM,pageInfo.getPageNum());
            data.put(Constants.PAGE_INFO_PAGE_SIZE,pageInfo.getPageSize());
            data.put(Constants.PAGE_INFO_PAGES,pageInfo.getPages());
            return getSuccessVo("",data);
        }
    }

    /**
     * 返回成功信息
     * @param msg 消息
     * @param data 数据
     * @return
     */
    public Map<String,Object> getSuccessVo(String msg, Object data) {
        return StringUtil.getResultVo(StatusCode.SUCCESS, StringUtil.trim(msg), data == null ? "" : data);
    }

    /**
     * 返回内部服务器异常信息
     * @return
     */
    public Map<String,Object> getErrorVo() {
        return StringUtil.getResultVo(StatusCode.INTERNAL_SERVER_ERROR,"INTERNAL_SERVER_ERROR","");
    }

    /**
     * 返回请求参数异常信息
     * @return
     */
    public Map<String,Object> getParamErrorVo() {
        return StringUtil.getResultVo(StatusCode.REQUEST_PARAM_ERROR,"REQUEST_PARAM_ERROR","");
    }

    /**
     * 返回请求参数重复信息
     * @param paramName 参数名
     * @return
     */
    public Map<String,Object> getParamRepeatErrorVo(String paramName) {
        return StringUtil.getResultVo(StatusCode.REQUEST_PARAM_REPEAT,"REQUEST_REPEAT_ERROR","提示 : '"+paramName+"'");
    }

    /**
     * 获得页码
     * @param pageNum
     * @return
     */
    public int getPageNum(int pageNum) {
        return pageNum < 1 ? PAGE_NUM : pageNum;
    }

    /**
     * 获得每页记录数
     * @param pageSize
     * @return
     */
    public int getPageSize(int pageSize) {
        return pageSize < 1 ? PAGE_SIZE : pageSize;
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
     * 获取用户所拥有的菜单模块
     * @return
     */
    public List<Module> getMenus() {
        List<Module> menus = Lists.newArrayList();
        List<Module> modules = getModules();
        if (!modules.isEmpty()) {
            for (Module module : modules) {
                if (module.getIsMenu() == Constants.IS_MENU) {
                    menus.add(module);
                }
            }
        }
        return menus;
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
     * 是否为编辑操作（若id不为"",则返回true；反之则返回false）
     * @param id 记录唯一表示标识
     * @return
     */
    public boolean isEdit(String id) {
        return "".equals(StringUtil.trim(id)) ? false : true;
    }

    /**
     * 以error方式记录日志
     * @param objectName
     * @param message
     */
    public static void loggerError(String objectName, String message) {
        LOGGER.error(objectName, message);
    }

    /**
     * 以error方式记录日志
     * @param objectName
     * @param throwable
     */
    public static void loggerError(String objectName, Throwable throwable) {
        loggerError(objectName, throwable.getMessage());
    }

    /**
     * 以info方式记录日志
     * @param objectName
     * @param message
     */
    public static void loggerInfo(String objectName, String message) {
        LOGGER.info(objectName, message);
    }

    /**
     * 以info方式记录日志
     * @param objectName
     * @param throwable
     */
    public static void loggerInfo(String objectName, Throwable throwable) {
        loggerInfo(objectName, throwable.getMessage());
    }

    /**
     * 以warning方式记录日志
     * @param objectName
     * @param message
     */
    public static void loggerWarning(String objectName, String message) {
        LOGGER.warn(objectName, message);
    }

    /**
     * 以warning方式记录日志
     * @param objectName
     * @param throwable
     */
    public static void loggerWarning(String objectName, Throwable throwable) {
        loggerWarning(objectName, throwable.getMessage());
    }

    /**
     * 以debug方式记录日志
     * @param objectName
     * @param message
     */
    public static void loggerDebug(String objectName, String message) {
        LOGGER.debug(objectName, message);
    }

    /**
     * 以debug方式记录日志
     * @param objectName
     * @param throwable
     */
    public static void loggerDebug(String objectName, Throwable throwable) {
        loggerDebug(objectName, throwable.getMessage());
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
     * 发送post请求
     * @param url
     * @param jsonParam
     * @param headParam
     * @return
     */
    public ResponseEntity<String> httpRequest(String url,String jsonParam,Map<String, String> headParam){
    	try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application","JSON",Charset.forName("UTF-8")));
			headers.setAccept(Arrays.asList(new MediaType[] {new MediaType("application","JSON",Charset.forName("UTF-8"))}));
			//请求头追加
			if(headParam != null) {
				for (Map.Entry<String, String> entry : headParam.entrySet()) {
					headers.add(entry.getKey(), entry.getValue());
				}
			}
			HttpEntity<String> requestEntity = new HttpEntity<String>(jsonParam,headers);
			HttpMethod post = HttpMethod.POST;
			ResponseEntity<String> exchange = new ResponseEntity<String>("",HttpStatus.OK);
			RestTemplate restTemplate = new RestTemplate();
			exchange = restTemplate.exchange(url, post,requestEntity,String.class);
			return exchange;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("",HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
