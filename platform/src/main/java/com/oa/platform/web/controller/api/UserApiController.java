package com.oa.platform.web.controller.api;

import com.oa.platform.biz.LogBiz;
import com.oa.platform.biz.RoleBiz;
import com.oa.platform.biz.UserBiz;
import com.oa.platform.common.Constants;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 用户信息API
 * @author Feng
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController extends BaseController {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    private LogBiz logBiz;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletRequest request;

    /**
     * 根据userId获得用户信息(包括详细信息)
     * @param userId 用户id
     * @return Map&lt;String,Object&gt;
     */
    @GetMapping("/get/{userId}")
    public Map<String,Object> get(@PathVariable String userId) {
        return userBiz.getUserById(userId);
    }

    /**
     * 根据userId获得用户详细信息
     * @param userId 用户id
     * @return Map&lt;String,Object&gt;
     */
    @GetMapping("/getDtl/{userId}")
    public Map<String,Object> getUserDtl(@PathVariable String userId) {
        return userBiz.getUserDtlById(userId);
    }

    /**
     * 根据用户名、密码获得用户信息
     * @param userName 不为空
     * @param userPwd 不为空
     * @return Map&lt;String,Object&gt;
     */
    @GetMapping("getUserByNameAndPwd")
    public Map<String,Object> getUserByNameAndPwd(@RequestParam String userName, @RequestParam String userPwd) {
        return userBiz.getUserByNameAndPwd(userName,userPwd);
    }

    /**
     * 检索用户信息
     * @param userId 用户id
     * @param userType 用户类型
     * @param userName 用户名
     * @param userNickname 用户昵称
     * @param recordFlag 信息标识
     * @param lastLoginTime 最后登录时间
     * @param recordTime 信息录入时间
     * @param updateTime 更新时间
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return Map&lt;String,Object&gt;
     */
    @GetMapping("search")
    public Map<String,Object> search(@RequestParam(defaultValue = "",required = false) String userId,
                                     @RequestParam(defaultValue = "",required = false) Integer userType,
                                     @RequestParam(defaultValue = "",required = false) String userName,
                                     @RequestParam(defaultValue = "",required = false) String userNickname,
                                     @RequestParam(defaultValue = "1",required = false) int recordFlag,
                                     @RequestParam(defaultValue = "",required = false) String lastLoginTime,
                                     @RequestParam(defaultValue = "",required = false) String recordTime,
                                     @RequestParam(defaultValue = "",required = false) String updateTime,
                                     @RequestParam(defaultValue = "",required = false) String key,
                                     @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
                                     @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return userBiz.search(userId,userType,userName,userNickname,
                recordFlag,lastLoginTime,recordTime,updateTime, key, pageNum,pageSize);
    }

    /**
     * 保存(或修改)角色信息
     * @param roleId 角色id(为null或空格，则为新增)
     * @param roleName 角色名称
     * @param roleDesc 角色描述
     * @param recordFlag 信息标识
     * @param userId 用户id
     * @return
     */
    @PostMapping("saveRole")
    public Map<String,Object> saveRole(
            @RequestParam(defaultValue = "",required = false) String roleId,
            @RequestParam(defaultValue = "",required = false) String roleName,
            @RequestParam(defaultValue = "",required = false) String roleDesc,
            @RequestParam(defaultValue = "1",required = false) Integer recordFlag,
            @RequestParam(defaultValue = "",required = false) String userId) {
        return roleBiz.saveRole(roleId, roleName, roleDesc, recordFlag, userId);
    }

    /**
     * 保存或修改模块信息
     * @param moduleId
     * @param parentId
     * @param moduleName
     * @param moduleDesc
     * @param moduleUrl
     * @param isLeaf
     * @param recordFlag
     * @param moduleCode
     * @param order
     * @return
     */
    @PostMapping("saveModule")
    public Map<String, Object> saveModule(
            @RequestParam(defaultValue = "",required = false) String moduleId,
            @RequestParam(defaultValue = "",required = false) String parentId,
            @RequestParam(defaultValue = "",required = false) String moduleName,
            @RequestParam(defaultValue = "",required = false) String moduleDesc,
            @RequestParam(defaultValue = "",required = false) String moduleUrl,
            @RequestParam(defaultValue = "",required = false) String isLeaf,
            @RequestParam(defaultValue = "1",required = false) String recordFlag,
            @RequestParam(defaultValue = "",required = false) String moduleCode,
            @RequestParam(defaultValue = "0",required = false) Integer order,
            @RequestParam(defaultValue = "",required = false) String moduleIcon,
            @RequestParam(defaultValue = "",required = false) String moduleStyle,
            @RequestParam(defaultValue = "0",required = false) Integer isMenu) {
        return roleBiz.saveModule(moduleId, parentId, moduleName,
                moduleDesc, moduleUrl, isLeaf, recordFlag, moduleCode, order,
                moduleIcon, moduleStyle, isMenu);
    }

    /**
     * 保存角色-模块信息
     * @param roleId 角色信息
     * @param moduleIds 模块信息
     * @return
     */
    @PostMapping("saveRoleModule")
    public Map<String, Object> saveRoleModule(@RequestParam String roleId, @RequestParam String[] moduleIds) {
        return roleBiz.saveRoleModule(roleId, moduleIds);
    }

    /**
     * 保存用户-角色信息
     * @param userId 用户id
     * @param roleIds 角色ids
     * @return
     */
    @PostMapping("saveUserRole")
    public Map<String, Object> saveUserRole(@RequestParam String userId, @RequestParam String[] roleIds) {
        return roleBiz.saveUserRole(userId, roleIds);
    }

    /**
     * 根据id删除用户-角色信息
     * @param id 关系信息id
     * @return
     */
    @PostMapping("deleteUserRoleById")
    public Map<String, Object> deleteUserRoleById(@RequestParam String id) {
        return roleBiz.deleteUserRoleById(id);
    }

    /**
     * 根据userId删除用户-角色信息
     * @param userId
     * @return
     */
    @PostMapping("deleteUserRoleByUserId")
    public Map<String, Object> deleteUserRoleByUserId(@RequestParam String userId) {
        return roleBiz.deleteUserRoleByUserId(userId);
    }

    /**
     * 根据id删除角色-模块信息
     * @param ids 指关系信息Id数组
     * @return
     */
    @PostMapping("deleteRoleModuleByIds")
    public Map<String, Object> deleteRoleModuleByIds(@RequestParam String... ids) {
        return roleBiz.deleteRoleModuleByIds(Arrays.asList(ids));
    }

    /**
     * 根据角色id删除角色-模块信息
     * @param roleId
     * @return
     */
    @PostMapping("deleteRoleModuleByRoleId")
    public Map<String, Object> deleteRoleModuleByRoleId(@RequestParam String roleId) {
        return roleBiz.deleteRoleModuleByRoleId(roleId);
    }

    /**
     * 更新角色信息标示
     * @param id
     * @param flag
     * @return
     */
    @PostMapping("updateRoleFlagById")
    public Map<String, Object> updateRoleFlagById(@RequestParam String id, @RequestParam Integer flag) {
        return roleBiz.updateRoleFlagById(id, flag);
    }

    /**
     * 更新模块信息标志
     * @param id
     * @param flag
     * @return
     */
    @PostMapping("updateModuleFlagById")
    public Map<String, Object> updateModuleFlagById(@RequestParam String id, @RequestParam String flag) {
        return roleBiz.updateModuleFlagById(id, flag);
    }

    /**
     * 获得所有角色信息
     * @return
     */
    @GetMapping("getAllRoles")
    public Map<String, Object> getAllRoles() {
        return roleBiz.getAllRoles();
    }

    /**
     * 获得模块信息
     * @param parentId 若为null或""，则查询所有
     * @return
     */
    @GetMapping("getModules")
    public Map<String, Object> getModules(@RequestParam(defaultValue = "",required = false) String parentId) {
        return roleBiz.getModules(parentId);
    }

    /**
     * 检索角色信息
     * @param roleId
     * @param roleName
     * @param recordFlag
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("searchRole")
    public Map<String, Object> searchRole(
            @RequestParam(defaultValue = "",required = false) String roleId,
            @RequestParam(defaultValue = "",required = false) String roleName,
            @RequestParam(defaultValue = "1",required = false) Integer recordFlag,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return roleBiz.searchRole(roleId, roleName, recordFlag, key, pageNum, pageSize);
    }

    /**
     * 检索模块信息
     * @param moduleId
     * @param parentId
     * @param moduleName
     * @param recordFlag
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("searchModule")
    public Map<String, Object> searchModule(
            @RequestParam(defaultValue = "",required = false) String moduleId,
            @RequestParam(defaultValue = "",required = false) String parentId,
            @RequestParam(defaultValue = "",required = false) String moduleName,
            @RequestParam(defaultValue = "1",required = false) Integer recordFlag,
            Integer isMenu,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return roleBiz.searchModule(moduleId, parentId, moduleName, recordFlag, isMenu, key, pageNum, pageSize);
    }

    /**
     * 检索角色-模块信息
     * @param recordId
     * @param roleId
     * @param moduleId
     * @param roleName
     * @param moduleName
     * @param fullModuleName
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("searchRoleModule")
    public Map<String, Object> searchRoleModule(
            @RequestParam(defaultValue = "",required = false) String recordId,
            @RequestParam(defaultValue = "",required = false) String roleId,
            @RequestParam(defaultValue = "",required = false) String moduleId,
            @RequestParam(defaultValue = "",required = false) String roleName,
            @RequestParam(defaultValue = "",required = false) String moduleName,
            @RequestParam(defaultValue = "",required = false) String fullModuleName,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return roleBiz.searchRoleModule(recordId, roleId, moduleId, roleName,
                moduleName, fullModuleName, key ,pageNum ,pageSize);
    }

    /**
     * 检索用户-角色信息
     * @param recordId
     * @param userId
     * @param roleId
     * @param userName
     * @param roleName
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("searchUserRole")
    public Map<String, Object> searchUserRole(
            @RequestParam(defaultValue = "",required = false) String recordId,
            @RequestParam(defaultValue = "",required = false) String userId,
            @RequestParam(defaultValue = "",required = false) String roleId,
            @RequestParam(defaultValue = "",required = false) String userName,
            @RequestParam(defaultValue = "",required = false) String roleName,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return roleBiz.searchUserRole(recordId, userId, roleId, userName, roleName, key, pageNum, pageSize);
    }

    /**
     * 检索用户-角色-模块信息
     * @param userId
     * @param userName
     * @param userNickname
     * @param roleId
     * @param roleName
     * @param moduleId
     * @param parentModuleId
     * @param moduleName
     * @param fullModuleName
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("searchUserRoleModule")
    public Map<String, Object> searchUserRoleModule(
            @RequestParam(defaultValue = "",required = false) String userId,
            @RequestParam(defaultValue = "",required = false) String userName,
            @RequestParam(defaultValue = "",required = false) String userNickname,
            @RequestParam(defaultValue = "",required = false) String roleId,
            @RequestParam(defaultValue = "",required = false) String roleName,
            @RequestParam(defaultValue = "",required = false) String moduleId,
            @RequestParam(defaultValue = "",required = false) String parentModuleId,
            @RequestParam(defaultValue = "",required = false) String moduleName,
            @RequestParam(defaultValue = "",required = false) String fullModuleName,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return roleBiz.searchUserRoleModule(userId, userName, userNickname, roleId, roleName, moduleId,
                parentModuleId, moduleName, fullModuleName, key, pageNum, pageSize);
    }

    /**
     * 根据角色id查询角色-模块信息
     * @param roleId
     * @return
     */
    @GetMapping("findRoleModuleByRoleId")
    public Map<String, Object> findRoleModuleByRoleId(@RequestParam String roleId) {
        return roleBiz.findRoleModuleByRoleId(roleId);
    }

    /**
     * 根据用户Id查询用户-角色信息
     * @param userId
     * @return
     */
    @GetMapping("findUserRoleByUserId")
    public Map<String, Object> findUserRoleByUserId(@RequestParam String userId) {
        return roleBiz.findUserRoleByUserId(userId);
    }

    /**
     * 保存用户基本信息
     * @param userType 用户类型
     * @param userName 用户名(登录名)
     * @param userNickname 用户昵称
     * @param userPwd 用户密码
     * @param langConfId 语言配置ID
     * @param recordFlag 信息标识
     * @return
     */
    @PostMapping("saveUserBaseInfo")
    public Map<String, Object> saveUserBaseInfo(
            @RequestParam(defaultValue = "",required = false) String userId,
            @RequestParam String userType,
            @RequestParam String userName,
            @RequestParam String userNickname,
            @RequestParam(defaultValue = "",required = false) String userPwd,
            @RequestParam(defaultValue = "",required = false) String langConfId,
            @RequestParam(defaultValue = "1",required = false) Integer recordFlag,
            @RequestParam(defaultValue = "",required = false) String oldPassword,
            @RequestParam(defaultValue = "",required = false) String passwordOrgi) {
        return userBiz.saveUserBaseInfo(userId, userType, userName, userNickname,
                userPwd, langConfId, recordFlag, oldPassword, passwordOrgi);
    }

    /**
     * 根据userId删除用户(逻辑)
     * @param userId 用户ID
     * @return
     */
    @PostMapping("deleteByUserId")
    public Map<String, Object> deleteByUserId(@RequestParam String userId) {
        return userBiz.updateUserRecordFlag(userId, Constants.INT_DEL);
    }

    /**
     * 根据userId更新用户状态(逻辑)
     * @param userId 用户ID
     * @param flag
     * @return
     */
    @PostMapping("updateUserFlag")
    public Map<String, Object> updateUserFlag(@RequestParam String userId,@RequestParam Integer flag) {
        return userBiz.updateUserRecordFlag(userId, flag);
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param password 密码
     * @param passwordOrgi 重复密码
     * @return
     */
    @PostMapping("modifyPwd")
    public Map<String, Object> modifyPwd(
            @RequestParam String userId,
            @RequestParam String password,
            @RequestParam String passwordOrgi) {
        return userBiz.modifyPwd(userId, password, passwordOrgi);
    }

    /**
     * 获得所有系统用户
     * @return
     */
    @GetMapping("allSysUsers")
    public Map<String, Object> getAllSysUsers() {
        return userBiz.getAllSysUsers();
    }

    /**
     * 获得所有系统用户(Map结构)
     * @return
     */
    @GetMapping("allSysUsersMap")
    public Map<String, Object> getAllSysUsersMap() {
        return userBiz.getAllSysUsersMap();
    }
}
