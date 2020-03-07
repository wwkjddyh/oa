package com.oa.platform.biz;

import com.alibaba.fastjson.JSONObject;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.service.LogService;
import com.oa.platform.service.RoleService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户信息业务逻辑处理
 * @author Feng
 * @date 2019/03/01
 */
@Component
public class UserBiz extends BaseBiz {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    LogService logService;

    /**
     * 保存用户信息
     * @param userType
     * @param userName
     * @param userNickname
     * @param userPwd
     * @param langConfId
     */
    public void saveUser(Integer userType,String userName,String userNickname,String userPwd,String langConfId) {
        User user = new User();
        user.setUserId(StringUtil.getRandomUUID());
        user.setUserType(userType);
        user.setUserName(userName);
        user.setUserNickname(userNickname);
        user.setUserPwdOrigi(userPwd);
        user.setUserPwd(userPwd);   //需要加密
        user.setRecordFlag(Constants.INT_NORMAL);
        user.setLangConfId(langConfId);
        user.setThirdPartyType("");
//        userService.saveUser(user);
        userService.save(user);
    }

    /**
     * 根据userId获得用户信息(包括详细信息, 但不显示密码信息)
     * @param userId 用户ID
     * @param conatinDtl (是否包含详细信息)
     * @return
     */
    public User getUserByUserId(String userId, boolean conatinDtl) {
        User user = null;
        userId = StringUtil.trim(userId);
        if(!"".equals(userId)) {
            user = userService.getById(userId);
            if(user != null) {
                user.setUserPwd("");
                user.setUserPwdOrigi("");
                if(conatinDtl) {
                    user.setUserDtl(userService.findDetailByUserId(userId));
                }
            }
        }
        return user;
    }

    /**
     * 根据userId获得用户详细信息
     * @param userId
     * @return
     */
    public UserDtl getUserDtlByUserId(String userId) {
        UserDtl userDtl = null;
        userId = StringUtil.trim(userId);
        if(!"".equals(userId)) {
            userDtl = userService.findDetailByUserId(userId);
        }
        return userDtl;
    }

    /**
     * <用于API接口>根据id获得用户(包括详细信息)
     * @param userId 用户id
     * @return
     */
    public Map<String, Object> getUserById(String userId) {
        userId = StringUtil.trim(userId);
        if("".equals(userId)) {
            ret = this.getParamErrorVo();
        }
        else {
           try {
               User user = getUserByUserId(userId, false);
               if(user == null) {
                   ret = this.getParamErrorVo();
               }
               else {
                   ret = this.getSuccessVo("", JSONObject.toJSONString(user));
               }
           }
           catch(Exception e) {
               loggerError(ThreadUtil.getCurrentFullMethodName(), e);
               ret = this.getErrorVo();
           }
        }
        return ret;
    }

    /**
     * <用于API接口>根据id获得用户详细信息
     * @param userId 用户id
     * @return
     */
    public Map<String,Object> getUserDtlById(String userId) {
        ret = null;
        userId = StringUtil.trim(userId);
        if("".equals(userId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                UserDtl userDtl = userService.findDetailByUserId(userId);
                /*
                if(userDtl == null) {
                    ret = this.getParamErrorVo();
                }
                else {

                }*/
                ret = this.getSuccessVo("", userDtl == null ? "" : JSONObject.toJSONString(userDtl));
            }
            catch(Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据用户名和密码查询用户信息
     * @param userName
     * @param userPwd
     * @return
     */
    public User findUserByNameAndPwd(String userName,String userPwd) {
        return userService.findUserByNameAndPwd(StringUtil.trim(userName),StringUtil.trim(userPwd));
    }

    /**
     * 根据用户名、密码获得用户信息
     * @param userName
     * @param userPwd
     * @return
     */
    public Map<String,Object> getUserByNameAndPwd(String userName,String userPwd) {
        ret = null;
        userName = StringUtil.trim(userName);
        userPwd = StringUtil.trim(userPwd);
        if(!"".equals(userName) && !"".equals(userPwd)) {
            User user = userService.findUserByNameAndPwd(userName,userPwd);
            if(user == null) {
                ret = getParamErrorVo();
            }
            else {
                ret = getSuccessVo("", JSONObject.toJSONString(user));
            }
        }
        else {
            ret = getParamErrorVo();
        }
        return ret;
    }

    /**
     * 检索用户信息
     * @param userId
     * @param userType
     * @param userName
     * @param userNickname
     * @param recordFlag
     * @param lastLoginTime
     * @param recordTime
     * @param updateTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Map<String,Object> search(String userId, Integer userType, String userName,
                                     String userNickname,Integer recordFlag, String lastLoginTime,
                                     String recordTime, String updateTime,int pageNum,int pageSize) {
        User user = new User();
        user.setUserId(StringUtil.trim(userId));
        user.setUserType(userType);
        user.setKey(userNickname);
        //user.setUserName(StringUtil.trim(userName));
        //user.setUserNickname(StringUtil.trim(userNickname));
        user.setRecordFlag(recordFlag);
        lastLoginTime = StringUtil.trim(lastLoginTime);
        recordTime = StringUtil.trim(recordTime);
        updateTime = StringUtil.trim(updateTime);
        if(!"".equals(lastLoginTime)) {
            user.setLastLoginTime(lastLoginTime);
        }
        if(!"".equals(recordTime)) {
            user.setRecordTime(recordTime);
        }
        if(!"".equals(updateTime)) {
            user.setUpdateTime(updateTime);
        }
        String isAdmin = "";
        if(userType != null && userType.intValue() == User.TYPE_ADMIN) {
            isAdmin = "1";
        }
        user.setIsAdmin(isAdmin);
        return getPageInfo(userService.search(user,getPageNum(pageNum),getPageSize(pageSize)));
    }
}
