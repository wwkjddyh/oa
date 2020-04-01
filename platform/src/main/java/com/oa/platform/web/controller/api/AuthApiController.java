package com.oa.platform.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oa.platform.biz.LogBiz;
import com.oa.platform.biz.RoleBiz;
import com.oa.platform.common.Constants;
import com.oa.platform.common.ResultVo;
import com.oa.platform.common.StatusCode;
import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.util.StringUtil;
import com.oa.platform.web.controller.BaseController;

/**
 * 权限API接口
 * @author Feng
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/api/auth")
public class AuthApiController extends BaseController {

    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletRequest request;

    @Autowired
  //  @Qualifier(value = "sessionRegistry")
    private SessionRegistry sessionRegistry;
    
    //短信权限码
    @Value("${phoneCode.appKey}")
	private String appKey;
    //短信权限码
    @Value("${phoneCode.masterSecret}")
	private String masterSecret;
    //获取验证码url
    @Value("${phoneCode.getSmsUrl}")
	private String smsUrl;
    //验证码校验url
    @Value("${phoneCode.validateSmsUrl}")
	private String validateSmsUrl;
    //模板id
    @Value("${phoneCode.temp_id}")
	private String temp_id;
    @Autowired
    private LogBiz logBiz;
    /**
     * 获取短信验证码
     * @param userId
     * @return
     */
    @GetMapping("getSMS")
    public ResultVo getSMS(String userId) {
    		if(userId == null || "".equals(userId)) {
    			return getErroResultVo(500,"请先填写登录名",null);
    		}
    		//一天内用户是否使用短信进行验证登录
    		List<String> userSms = roleBiz.getUserSMSData(userId);
    		if(userSms == null || userSms.size() == 0) {
	    		//获取用户手机号码
	    		List<String> userPhone = roleBiz.getUserPhoneByUserName(userId);
	    		if(userPhone != null && userPhone.size()>0) {
	    			Map<String,String> params = new HashMap<String,String>();
					params.put("mobile", userPhone.get(0));
					params.put("temp_id", temp_id);
					//post请求获取短信验证码
					//roleBiz.httpRequest(smsUrl, JSONObject.toJSONString(params), appKey,masterSecret);
					roleBiz.getSms(smsUrl, JSONObject.toJSONString(params), appKey,masterSecret);
	    		}else {
	    			return getErroResultVo(500,"当前用户无手机号码或无效",null);
	    		}
    		}
		
    	return getSuccessResultVo(null);
    }
    /**
     * 是否短信验证
     * @param userId
     * @return
     */
    @GetMapping("needSms")
    public ResultVo needSms(String userId) {
    	List<String> userSms = roleBiz.getUserSMSData(userId);
		if(userSms == null || userSms.size() == 0) {
			return getSuccessResultVo(true);
		}else {
			return getSuccessResultVo(false);
		}
    }
    /**
     * 异步登录
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @PostMapping("/login")
    public Map<String,Object> login(@RequestParam String username, @RequestParam String password) {
        //登录 身份认证
        // 这句代码会自动执行咱们自定义的 "MyUserDetailService.java" 身份认证类
        //1: 将用户名和密码封装成UsernamePasswordAuthenticationToken  new UsernamePasswordAuthenticationToken(username, password)
        //2: 将UsernamePasswordAuthenticationToken传给AuthenticationManager进行身份认证   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAccount, userPwd));
        //3: 认证完毕，返回一个认证后的身份： Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAccount, userPwd));
        //4: 认证后，存储到SecurityContext里   SecurityContext securityContext = SecurityContextHolder.getContext();securityContext.setAuthentication(authentication);

        //UsernamePasswordAuthenticationToken继承AbstractAuthenticationToken实现Authentication
        //当在页面中输入用户名和密码之后首先会进入到UsernamePasswordAuthenticationToken验证(Authentication)，注意用户名和登录名都是页面传来的值
        //然后生成的Authentication会被交由AuthenticationManager来进行管理
        //而AuthenticationManager管理一系列的AuthenticationProvider，
        //而每一个Provider都会通UserDetailsService和UserDetail来返回一个
        //以UsernamePasswordAuthenticationToken实现的带用户名和密码以及权限的Authentication
        try {
//            List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
//            System.err.println("allPrincipals:"+ JSONObject.toJSONString(allPrincipals));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute(Constants.SPRING_SECURITY_SESSION_KEY, securityContext); // 这个非常重要，否则验证后将无法登陆
            Map<String, Object> data = new HashMap<>();
            if(authentication != null) {
                String name = StringUtil.trim(authentication.getName());
                if(!"".equals(name)) {
                    Object principal = authentication.getPrincipal();
                    if(principal != null) {
                        User user = (User) principal;
                        data.put("nickname", user.getUserNickname());
                        data.put("type", user.getTypeName());
                        data.put("status", user.getStatusName());
                        data.put("lastLogonTime", user.getLastLoginTime());
                        UserDtl userDtl = user.getUserDtl();
                        String email = "", addr = "", alipay = "", qq = "", wechat = "", weibo = "", blog = "";
//                        if(userDtl != null) {
//                            email = userDtl.getUserEmail();
//                            addr = userDtl.getUserAddr();
//                            alipay = userDtl.getUserAlipay();
//                            qq = userDtl.getUserQq();
//                            wechat = userDtl.getUserWechat();
//                            weibo = userDtl.getUserWeibo();
//                            blog = userDtl.getUserBlog();
//                        }
                        data.put("email", email);
                        data.put("addr", addr);
                        data.put("alipay", alipay);
                        data.put("qq", qq);
                        data.put("wechat", wechat);
                        data.put("weibo", weibo);
                        data.put("blog", blog);
                        data.put("userId", user.getUserId());
                        String ipAddr = "";
                        if(authentication.getDetails() != null) {
                            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
                            ipAddr = details.getRemoteAddress();
                        }
                        //保存登录信息
                        logBiz.saveLoginLog(user.getUserId(),ipAddr);
                    }
                }
            }

            return StringUtil.getLoginResultVo(StatusCode.SUCCESS,"登录成功", session.getId(), data);
        }
        catch(Exception e) {
            e.printStackTrace();
            return StringUtil.getResultVo(StatusCode.UNAUTHORIZED,"用户名或密码错误","");
        }
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logout",method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String,Object> logout() {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute(Constants.SPRING_SECURITY_SESSION_KEY);
        if(securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if(authentication != null) {
                authentication.setAuthenticated(false);
            }
            securityContext.setAuthentication(null);
            session.removeAttribute(Constants.SPRING_SECURITY_SESSION_KEY);
        }
        return StringUtil.getResultVo(StatusCode.SUCCESS,"登出成功","");
    }

    /**
     * 保存(或修改)角色信息
     * @param roleId 角色id(为null或空格，则为新增)
     * @param roleName 角色名称
     * @param roleDesc 角色描述
     * @param recordFlag 信息标识
     * @return
     */
    @PostMapping("saveRole")
    public Map<String,Object> saveRole(@RequestParam(defaultValue = "",required = false) String roleId,
                                       @RequestParam String roleName,
                                       @RequestParam(defaultValue = "",required = false) String roleDesc,
                                       @RequestParam(defaultValue = "1",required = false) Integer recordFlag) {
        return roleBiz.saveRole(roleId,roleName,roleDesc,recordFlag,this.getUserIdOfSecurity());
    }

    /**
     * 根据用户ID查询模块信息
     * @param userId 用户ID
     * @param isMenu 是否为菜单(0,否;1,是)
     * @return
     */
    @GetMapping("findModuleByUserId")
    public Map<String, Object> findModuleByUserId(@RequestParam String userId, Integer isMenu) {
        return roleBiz.findModuleByUserId(userId, isMenu);
    }

    /**
     * 获得用户所拥有的所有模块信息
     * @return
     */
    @GetMapping("getOwnedModules")
    public Map<String, Object> getOwnedModules() {
        return roleBiz.getOwnedModules();
    }

    /**
     * 获得当前用户信息
     * @return
     */
    @GetMapping("getCurrentUser")
    public Map<String, Object> getCurrentUser() {
        return roleBiz.getCurrentUser();
    }
}
