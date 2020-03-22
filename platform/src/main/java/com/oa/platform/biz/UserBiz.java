package com.oa.platform.biz;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oa.platform.common.Constants;
import com.oa.platform.common.StatusCode;
import com.oa.platform.entity.Area;
import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.service.LogService;
import com.oa.platform.service.RoleService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
     * @param key 关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Map<String,Object> search(String userId, Integer userType, String userName,
                                     String userNickname,Integer recordFlag, String lastLoginTime,
                                     String recordTime, String updateTime, String key, int pageNum,int pageSize) {
        User user = new User();
        user.setUserId(StringUtil.trim(userId));
        user.setUserType(userType);
        user.setKey(StringUtil.trim(key));
        user.setUserName(StringUtil.trim(userName));
        user.setUserNickname(StringUtil.trim(userNickname));
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

    /**
     * 是否重复
     * @param userId 用户ID
     * @param userName 用户名(登录名)
     * @return
     */
    boolean validRepeat(String userId, String userName) {
        boolean isRepeat = false;
        userId = StringUtil.trim(userId);
        userName = StringUtil.trim(userName);
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        List<User> userList = userService.find(user);
        if(userList != null && !userList.isEmpty()) {
            List<User> users = Lists.newArrayList();
            for (User u : userList) {
                if (u.getRecordFlag() != Constants.INT_DEL) {
                    users.add(u);
                }
            }
            if (!users.isEmpty()) {
                if("".equals(userId)) {
                    isRepeat = true;
                }
                else {
                    final String finalUserId = userId;
                    isRepeat = users.parallelStream().anyMatch(e -> !e.getUserId().equals(finalUserId));
                }
            }

        }
        return isRepeat;
    }

    /**
     * 保存用户基本信息
     * @param userType 用户类型
     * @param userName 用户名(登录名)
     * @param userNickname 用户昵称
     * @param userPwd 用户密码
     * @param langConfId 语言配置ID
     * @param recordFlag 信息标识
     * @param oldPassword 原密码(密码修改时校验)
     * @param passwordOrgi 再次密码(密码修改时校验)
     * @return
     */
    public Map<String, Object> saveUserBaseInfo(String userId, String userType, String userName, String userNickname,
                                                String userPwd, String langConfId, Integer recordFlag,
                                                String oldPassword, String passwordOrgi) {
        userId = StringUtil.trim(userId);
        userName = StringUtil.trim(userName);
        userType = StringUtil.trim(userType, User.TYPE_PERSON + "");
        userPwd = StringUtil.trim(userPwd);
        langConfId = StringUtil.trim("");
        try {
            if ("".equals(userName)) {
                ret = this.getParamErrorVo();
            }
            else {
                boolean isRepeat = validRepeat(userId, userName);
                if (isRepeat) {
                    ret = this.getParamRepeatErrorVo("用户名");
                }
                else {
                    User user = new User();
                    user.setUserName(userName);
                    user.setUserNickname(StringUtil.trim(userNickname));
                    user.setUserPwd(SecurityUtil.encodeBCryptPassword(userPwd));
                    user.setUserPwdOrigi(userPwd);
                    user.setLangConfId(langConfId);
                    user.setUserType(Integer.parseInt(userType));
                    if ("".equals(userId)) {
                        user.setUserPwd(StringUtil.trim(userPwd, "123456"));
                        user.setUserId(StringUtil.getRandomUUID());
                        user.setRecordFlag(Constants.INT_NORMAL);
                        userService.save(user);
                        ret = this.getSuccessVo("", "");
                    }
                    else {
                        oldPassword = StringUtil.trim(oldPassword);
                        passwordOrgi = StringUtil.trim(passwordOrgi);
                        User tUser = userService.getById(userId);
                        boolean isValidPwd = true;
                        String msg = "";
                        if ("".equals(userPwd) && "".equals(oldPassword) && "".equals(passwordOrgi)) {
                            isValidPwd = true;
                        }
                        else {
                            if ("".equals(userPwd) || "".equals(oldPassword) || "".equals(passwordOrgi)) {
                                msg = "'密码'、'原始密码'、'重复密码'均不能为空或空格";
                                isValidPwd = false;
                            }
                            else if (userPwd.length() > 128 || userPwd.length() < 6) {
                                msg = "'密码'的长度在 6 到 128 个字符之间";
                                isValidPwd = false;
                            }
                            else if (oldPassword.length() > 128 || oldPassword.length() < 6) {
                                msg = "'原始密码'的长度在 6 到 128 个字符之间";
                                isValidPwd = false;
                            }
                            else if (passwordOrgi.length() > 128 || passwordOrgi.length() < 6) {
                                msg = "'重复密码'的长度在 6 到 128 个字符之间";
                                isValidPwd = false;
                            }
                            else if (!userPwd.equals(passwordOrgi)) {
                                msg = "'密码'与'重复密码'不相同";
                                isValidPwd = false;
                            }
                            else if (!oldPassword.equals(tUser.getUserPwdOrigi())) {
                                msg = "'旧密码'与'原始密码'不相同";
                                isValidPwd = false;
                            }
                        }

                        if (isValidPwd) {
                            user.setRecordFlag(recordFlag == null ? Constants.INT_NORMAL : recordFlag);
                            user.setUserId(userId);
                            userService.update(user);
                            ret = this.getSuccessVo("", "");
                        }
                        else {
                            ret = StringUtil.getResultVo(StatusCode.REQUEST_PARAM_ERROR, msg, "");
                        }

                    }

                }
            }
        } catch (Exception e) {
            ret = this.getErrorVo();
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 更新用户状态
     * @param userId 用户ID
     * @param recordFlag 状态(1,正常;0,删除;-1,冻结)
     * @return
     */
    public Map<String, Object> updateUserRecordFlag(String userId, Integer recordFlag) {
        userId = StringUtil.trim(userId);
        if ("".equals(userId) || recordFlag == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                User user = new User();
                user.setUserId(userId);
                user.setRecordFlag(recordFlag);
                user.setUpdateTime(DateUtil.currDateFormat(null));
                userService.update(user);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                ret = this.getErrorVo();
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param password 密码
     * @param passwordOrgi 重复密码
     * @return
     */
    public Map<String, Object> modifyPwd(String userId, String password, String passwordOrgi) {
        userId = StringUtil.trim(userId);
        password = StringUtil.trim(password);
        passwordOrgi = StringUtil.trim(passwordOrgi);
        try {
            if ("".equals(userId)) {
                ret = this.getParamErrorVo();
            }
            else {
                User tUser = userService.getById(userId);
                if (tUser == null) {
                    ret = this.getParamErrorVo();
                }
                else {
                    boolean isValidPwd = true;
                    String msg = "";
                    if ("".equals(password) || "".equals(passwordOrgi)) {
                        msg = "'密码'、'重复密码'均不能为空或空格";
                        isValidPwd = false;
                    }
                    else if (password.length() > 128 || password.length() < 6) {
                        msg = "'密码'的长度在 6 到 128 个字符之间";
                        isValidPwd = false;
                    }
                    else if (passwordOrgi.length() > 128 || passwordOrgi.length() < 6) {
                        msg = "'重复密码'的长度在 6 到 128 个字符之间";
                        isValidPwd = false;
                    }
                    else if (!password.equals(passwordOrgi)) {
                        msg = "'密码'与'重复密码'不相同";
                        isValidPwd = false;
                    }

                    if (isValidPwd) {
                        User user = new User();
                        user.setUserId(userId);
                        user.setUserPwd(SecurityUtil.encodeBCryptPassword(password));
                        user.setUserPwdOrigi(password);
                        userService.update(user);
                        ret = this.getSuccessVo("", "");
                    }
                    else {
                        ret = StringUtil.getResultVo(StatusCode.REQUEST_PARAM_ERROR, msg, "");
                    }
                }
            }
        } catch (Exception e) {
            ret = this.getErrorVo();
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param password 密码
     * @param oldPassword 原始密码
     * @param passwordOrgi 重复密码
     * @return
     */
    public Map<String, Object> modifyPwd(String userId, String password, String oldPassword, String passwordOrgi) {
        userId = StringUtil.trim(userId);
        password = StringUtil.trim(password);
        oldPassword = StringUtil.trim(oldPassword);
        passwordOrgi = StringUtil.trim(passwordOrgi);
        try {
            if ("".equals(userId)) {
                ret = this.getParamErrorVo();
            }
            else {
                User tUser = userService.getById(userId);
                if (tUser == null) {
                    ret = this.getParamErrorVo();
                }
                else {
                    boolean isValidPwd = true;
                    String msg = "";
                    if ("".equals(password) || "".equals(oldPassword) || "".equals(passwordOrgi)) {
                        msg = "'密码'、'原始密码'、'重复密码'均不能为空或空格";
                        isValidPwd = false;
                    }
                    else if (password.length() > 128 || password.length() < 6) {
                        msg = "'密码'的长度在 6 到 128 个字符之间";
                        isValidPwd = false;
                    }
                    else if (oldPassword.length() > 128 || oldPassword.length() < 6) {
                        msg = "'原始密码'的长度在 6 到 128 个字符之间";
                        isValidPwd = false;
                    }
                    else if (passwordOrgi.length() > 128 || passwordOrgi.length() < 6) {
                        msg = "'重复密码'的长度在 6 到 128 个字符之间";
                        isValidPwd = false;
                    }
                    else if (!password.equals(passwordOrgi)) {
                        msg = "'密码'与'重复密码'不相同";
                        isValidPwd = false;
                    }
                    else if (!oldPassword.equals(tUser.getUserPwdOrigi())) {
                        msg = "'旧密码'与'原始密码'不相同";
                        isValidPwd = false;
                    }

                    if (isValidPwd) {
                        User user = new User();
                        user.setUserId(userId);
                        user.setUserPwd(SecurityUtil.encodeBCryptPassword(password));
                        user.setUserPwdOrigi(password);
                        userService.update(user);
                        ret = this.getSuccessVo("", "");
                    }
                    else {
                        ret = StringUtil.getResultVo(StatusCode.REQUEST_PARAM_ERROR, msg, "");
                    }
                }
            }
        } catch (Exception e) {
            ret = this.getErrorVo();
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获得所有系统用户
     * @return
     */
    public Map<String, Object> getAllSysUsers() {
        try {
            User user = new User();
            user.setUserType(User.TYPE_ADMIN);
            user.setRecordFlag(Constants.INT_NORMAL);
            List<User> users = userService.find(user);
            users = users == null ? Lists.newArrayList() : users;
            if (!users.isEmpty()) {
                int len = users.size();
                for (int i = 0; i < len; i ++) {
                    User u = users.get(i);
                    u.setUserPwd("");
                    u.setUserPwdOrigi("");
                }
            }
            ret = this.getSuccessVo("", users);
        } catch (Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 获得所有系统用户(Map结构)
     * @return
     */
    public Map<String, Object> getAllSysUsersMap() {
        try {
            Map<String, Map<String, Object>> userMap = Maps.newHashMap();
            User user = new User();
            user.setUserType(User.TYPE_ADMIN);
            user.setRecordFlag(Constants.INT_NORMAL);
            List<User> users = userService.find(user);
            users = users == null ? Lists.newArrayList() : users;
            if (!users.isEmpty()) {
                int len = users.size();
                for (int i = 0; i < len; i ++) {
                    User u = users.get(i);
                    String userId = u.getUserId();
                    Map<String, Object> entry = Maps.newHashMap();
                    entry.put("userId", userId);
                    entry.put("userName", u.getUserName());
                    entry.put("userNickname", u.getUserNickname());
                    userMap.put(userId, entry);
                }
            }
            ret = this.getSuccessVo("", userMap);
        } catch (Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
