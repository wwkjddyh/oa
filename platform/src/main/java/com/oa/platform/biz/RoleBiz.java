package com.oa.platform.biz;

import com.oa.platform.common.Constants;
import com.oa.platform.entity.*;
import com.oa.platform.service.RoleService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 角色及模块
 * @author Feng
 * @date 2019/03/01
 */
@Component
public class RoleBiz extends BaseBiz {

    @Autowired
    private RoleService roleService;

    /**
     * 保存(或修改)角色信息
     * @param roleId 角色id(为null或空格，则为新增)
     * @param roleName 角色名称
     * @param roleDesc 角色描述
     * @param recordFlag 信息标识
     * @param userId 用户id
     * @return
     */
    public Map<String,Object> saveRole(String roleId, String roleName, String roleDesc,Integer recordFlag,String userId) {
        roleId = StringUtil.trim(roleId);
        roleName = StringUtil.trim(roleName);

        ret = null;
        try {
            if("".equals(roleName)) {
                ret = this.getParamErrorVo();
            }
            else {
                roleDesc = StringUtil.trim(roleDesc);
                recordFlag = recordFlag == null ? Constants.INT_NORMAL : recordFlag;
                List<Role> roles = roleService.findRoleByRoleName(roleName);
                if("".equals(roleId)) { //新增
                    if(roles.isEmpty()) {
                        Role role = new Role();
                        role.setRoleId(StringUtil.getRandomUUID());
                        role.setRoleName(roleName);
                        role.setRoleDesc(roleDesc);
                        role.setRecordFlag(recordFlag);
                        roleService.saveRole(role);
                        ret = this.getSuccessVo("","");
                    }
                    else {
                        ret = this.getParamRepeatErrorVo("角色名称");
                    }
                }
                else {
                    Role role = new Role();
                    role.setRecordFlag(recordFlag);
                    role.setUpdateTime(DateUtil.currDateFormat(null));
                    role.setUpdateUserId(userId);
                    role.setRoleName(roleName);
                    role.setRoleDesc(roleDesc);
                    role.setRoleId(roleId);
                    if(roles.isEmpty()) {   //更新
                        roleService.updateRole(role);
                        ret = this.getSuccessVo("","");
                    }
                    else {  //验证是是否重复
                        /*
                        boolean isRepeat = false;
                        for(Role entry : roles) {
                            if(!entry.getRoleId().equals(roleId) && entry.getRoleName().equals(roleName)) {
                                isRepeat = true;
                                break;
                            }
                        }*/
                        final String finalRoleId = roleId, finalRoleName = roleName;
                        boolean isRepeat = roles.parallelStream().anyMatch(r ->
                                !Objects.equals(r.getRoleId(), finalRoleId)
                                        && Objects.equals(r.getRoleName(), finalRoleName));

                        if(isRepeat) {
                            ret = this.getParamRepeatErrorVo("角色名称");
                        }
                        else {
                            roleService.updateRole(role);
                            ret = this.getSuccessVo("","");
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
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
     * @param moduleIcon
     * @param moduleStyle
     * @param isMenu
     * @return
     */
    public Map<String, Object> saveModule(String moduleId, String parentId, String moduleName, String moduleDesc,
                                          String moduleUrl, String isLeaf, String recordFlag, String moduleCode,
                                          Integer order, String moduleIcon, String moduleStyle, Integer isMenu) {
        moduleName = StringUtil.trim(moduleName);
        if("".equals(moduleName)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                boolean isRepeat = false;
                moduleId = StringUtil.trim(moduleId);
                boolean isEdit = isEdit(moduleId);

                parentId = StringUtil.trim(parentId);
                Module module = new Module();
                module.setParentId(parentId);
                module.setModuleName(moduleName);
                List<Module> modules = roleService.findModule(module);
                if(modules != null && !modules.isEmpty()) {
                    if(!isEdit) {   //  新增
                        isRepeat = true;
                    }
                    else {  // 修改
                        final String finalModuleId = moduleId;
                        isRepeat = modules.parallelStream().anyMatch(e -> !e.getModuleId().equals(finalModuleId));
                    }
                }
                if(isRepeat) {
                    ret = this.getParamRepeatErrorVo("角色名称");
                }
                else {
                    module.setOrder(order == null ? 0 : order);
                    module.setModuleIcon(StringUtil.trim(moduleIcon));
                    module.setModuleStyle(StringUtil.trim(moduleStyle));
                    module.setModuleCode(StringUtil.trim(moduleCode));
                    module.setIsMenu(isMenu == null ? Constants.IS_NOT_MENU : isMenu);
                    module.setModuleDesc(moduleDesc);
                    module.setModuleUrl(StringUtil.trim(moduleUrl));
                    module.setIsLeaf(Integer.parseInt(StringUtil.trim(isLeaf, "1")));
                    module.setRecordFlag(Integer.parseInt(StringUtil.trim(recordFlag, Constants.INT_NORMAL + "")));
                    String dateTime = DateUtil.currDateFormat(null);
                    if(!isEdit) {
                        module.setModuleId(StringUtil.getRandomUUID());
                        module.setRecordTime(dateTime);
                        roleService.saveModule(module);
                    }
                    else {
                        module.setModuleId(moduleId);
                        module.setUpdateTime(dateTime);
                        module.setUpdateUserId(this.getUserIdOfSecurity());
                        roleService.updateModule(module);
                    }
                    ret = this.getSuccessVo("", "");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 保存角色-模块信息
     * @param roleId 角色信息
     * @param moduleIds 模块信息
     * @return
     */
    public Map<String, Object> saveRoleModule(String roleId, String... moduleIds) {
        roleId = StringUtil.trim(roleId);
        if("".equals(roleId) || moduleIds == null || moduleIds.length <= 0) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                roleService.deleteRoleModuleByRoleId(roleId);
                String dateTime = DateUtil.currDateFormat(null);
                List<RoleModule> roleModules = new ArrayList<>();
                for(String moduleId : moduleIds) {
                    RoleModule roleModule = new RoleModule();
                    roleModule.setRecordId(StringUtil.getRandomUUID());
                    roleModule.setRoleId(roleId);
                    roleModule.setModuleId(moduleId);
                    roleModule.setRecordTime(dateTime);
                    roleModules.add(roleModule);
                }
                roleService.batchSaveRoleModule(roleModules);
                ret = this.getSuccessVo("", null);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 保存用户-角色信息
     * @param userId 用户id
     * @param roleIds 角色ids
     * @return
     */
    public Map<String, Object> saveUserRole(String userId, String... roleIds) {
        userId = StringUtil.trim(userId);
        if("".equals(userId) || roleIds == null || roleIds.length <= 0) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                roleService.deleteUserRoleByUserId(userId);
                String dateTime = DateUtil.currDateFormat(null);
                List<UserRole> userRoles = new ArrayList<>(0);
                for(String roleId : roleIds) {
                    UserRole userRole = new UserRole();
                    userRole.setRecordId(StringUtil.getRandomUUID());
                    userRole.setUserId(userId);
                    userRole.setRecordId(roleId);
                    userRole.setRecordTime(dateTime);
                    userRoles.add(userRole);
                }
                roleService.batchSaveUserRole(userRoles);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据id删除用户-角色信息
     * @param id 关系信息id
     * @return
     */
    public Map<String, Object> deleteUserRoleById(String id) {
        id = StringUtil.trim(id);
        if("".equals(id)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                roleService.deleteUserRoleById(id);
                ret = this.getSuccessVo("", null);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据userId删除用户-角色信息
     * @param userId
     * @return
     */
    public Map<String, Object> deleteUserRoleByUserId(String userId) {
        userId = StringUtil.trim(userId);
        if("".equals(userId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                roleService.deleteUserRoleByUserId(userId);
                ret = this.getSuccessVo("", null);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据id删除角色-模块信息
     * @param ids 指关系信息Id数组
     * @return
     */
    public Map<String, Object> deleteRoleModuleByIds(List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                roleService.batchDeleteRoleModuleByIds(ids);
                ret = this.getSuccessVo("", null);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据角色id删除角色-模块信息
     * @param roleId
     * @return
     */
    public Map<String, Object> deleteRoleModuleByRoleId(String roleId) {
        roleId = StringUtil.trim(roleId);
        if("".equals(roleId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                roleService.deleteRoleModuleByRoleId(roleId);
                ret = this.getSuccessVo("", null);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 更新角色信息标示
     * @param id
     * @param flag
     * @return
     */
    public Map<String, Object> updateRoleFlagById(String id, Integer flag) {
        id = StringUtil.trim(id);
        if("".equals(id) || flag == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Role role = new Role();
                role.setRoleId(id);
                role.setRecordFlag(flag);
                roleService.updateRole(role);
                ret = this.getSuccessVo("", null);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 更新模块信息标志
     * @param id
     * @param flag
     * @return
     */
    public Map<String, Object> updateModuleFlagById(String id, String flag) {
        id = StringUtil.trim(id);
        flag = StringUtil.trim(flag);
        if("".equals(id) || "".equals(flag)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                roleService.updateModuleFlagById(id ,flag);
                ret = this.getSuccessVo("", null);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 获得所有角色信息
     * @return
     */
    public Map<String, Object> getAllRoles() {
        try {
            Role role = new Role();
            role.setRecordFlag(Constants.INT_NORMAL);
            List<Role> roles = roleService.findRole(role);
            ret = this.getSuccessVo("", roles);
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 获得模块信息
     * @param parentId 若为null或""，则查询所有
     * @return
     */
    public Map<String, Object> getModules(String parentId) {
        try {
            Module module = new Module();
            module.setParentId(parentId);
            module.setRecordFlag(Constants.INT_NORMAL);
            List<Module> modules = roleService.findModule(module);
            ret = this.getSuccessVo("", modules);
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
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
    public Map<String, Object> searchRole(String roleId, String roleName, Integer recordFlag, String key,
                                          int pageNum, int pageSize) {
        try {
            Role role = new Role();
            role.setRoleId(StringUtil.trim(roleId));
            role.setRoleName(StringUtil.trim(roleName));
            role.setKey(StringUtil.trim(key));
            role.setRecordFlag(recordFlag);
            ret = this.getPageInfo(roleService.searchRole(role, pageNum, pageSize));
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 检索模块信息
     * @param moduleId
     * @param parentId
     * @param moduleName
     * @param recordFlag
     * @param isMenu
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Map<String, Object> searchModule(String moduleId, String parentId, String moduleName, Integer recordFlag,
                                            Integer isMenu, String key, int pageNum, int pageSize) {
        try {
            Module module = new Module();
            module.setModuleId(StringUtil.trim(moduleId));
            module.setParentId(StringUtil.trim(parentId));
            module.setModuleName(StringUtil.trim(moduleName));
            module.setRecordFlag(recordFlag);
            module.setIsMenu(isMenu);
            module.setKey(StringUtil.trim(key));
            ret = this.getPageInfo(roleService.searchModule(module, pageNum, pageSize));
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
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
    public Map<String, Object> searchRoleModule(String recordId, String roleId, String moduleId, String roleName,
                                                String moduleName, String fullModuleName, String key,
                                                int pageNum, int pageSize) {
        try {
            RoleModule roleModule = new RoleModule();
            roleModule.setRecordId(StringUtil.trim(recordId));
            roleModule.setRoleId(StringUtil.trim(roleId));
            roleModule.setModuleId(StringUtil.trim(moduleId));
            roleModule.setRoleName(StringUtil.trim(roleName));
            roleModule.setModuleName(StringUtil.trim(moduleName));
            roleModule.setFullModuleName(StringUtil.trim(fullModuleName));
            roleModule.setKey(StringUtil.trim(key));
            ret = this.getPageInfo(roleService.searchRoleModule(roleModule, pageNum, pageSize));
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
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
    public Map<String, Object> searchUserRole(String recordId, String userId, String roleId, String userName,
                                              String roleName, String key, int pageNum, int pageSize) {
        try {
            UserRole userRole = new UserRole();
            userRole.setRecordId(StringUtil.trim(recordId));
            userRole.setUserId(StringUtil.trim(userId));
            userRole.setRoleId(StringUtil.trim(roleId));
            userRole.setUserName(StringUtil.trim(userName));
            userRole.setRoleName(StringUtil.trim(roleName));
            userRole.setKey(StringUtil.trim(key));
            ret = this.getPageInfo(roleService.searchUserRole(userRole, pageNum, pageSize));
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
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
    public Map<String, Object> searchUserRoleModule(String userId, String userName, String userNickname, String roleId,
                                                    String roleName, String moduleId, String parentModuleId,
                                                    String moduleName, String fullModuleName, String key,
                                                    int pageNum, int pageSize) {
        try {
            UserRoleModule userRoleModule = new UserRoleModule();
            userRoleModule.setUserId(StringUtil.trim(userId));
            userRoleModule.setUserName(StringUtil.trim(userName));
            userRoleModule.setUserNickname(StringUtil.trim(userNickname));
            userRoleModule.setRoleId(StringUtil.trim(roleId));
            userRoleModule.setRoleName(StringUtil.trim(roleName));
            userRoleModule.setModuleId(StringUtil.trim(moduleId));
            userRoleModule.setParentModuleId(StringUtil.trim(parentModuleId));
            userRoleModule.setModuleName(StringUtil.trim(moduleName));
            userRoleModule.setFullModuleName(StringUtil.trim(fullModuleName));
            userRoleModule.setKey(StringUtil.trim(key));
            ret = this.getPageInfo(roleService.searchUserRoleModule(userRoleModule, pageNum, pageSize));
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 根据角色id查询角色-模块信息
     * @param roleId 若为null或"",则不进行查询
     * @return
     */
    public Map<String, Object> findRoleModuleByRoleId(String roleId) {
        roleId = StringUtil.trim(roleId);
        if("".equals(roleId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                RoleModule roleModule = new RoleModule();
                roleModule.setRoleId(roleId);
                ret = this.getSuccessVo("", roleService.findRoleModule(roleModule));
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据用户Id查询用户-角色信息
     * @param userId
     * @return
     */
    public Map<String, Object> findUserRoleByUserId(String userId) {
        userId = StringUtil.trim(userId);
        if("".equals(userId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                ret = this.getSuccessVo("", roleService.findUserRole(userRole));

            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据用户ID查询模块信息
     * @param userId 用户ID
     * @param isMenu 是否为菜单
     * @return
     */
    public Map<String, Object> findModuleByUserId(String userId, Integer isMenu) {
        userId = StringUtil.trim(userId);
        if ("".equals(userId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                ret = this.getSuccessVo("", roleService.findModuleByUserId(userId, isMenu));
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 获得当前用户所拥有的所有模块信息
     * @return
     */
    public Map<String, Object> getOwnedModules() {
        try {
            ret = this.getSuccessVo("", getModules());
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
