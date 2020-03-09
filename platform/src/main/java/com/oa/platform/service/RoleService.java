package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.*;

import java.util.List;

/**
 * 角色信息
 * @author Feng
 * @date 2018/08/23
 */
public interface RoleService {

    /**
     * 保存角色信息
     * @param role
     */
    void saveRole(Role role);

    /**
     * 根据Id更新信息标识
     * @param roleId
     * @param recordFlag
     */
    void updateRoleFlagById(String roleId, String recordFlag);

    /**
     * 批量保存用户-角色信息
     * @param userRoles
     */
    void batchSaveUserRole(List<UserRole> userRoles);

    /**
     * 删除用户-角色信息
     * @param recordId
     */
    void deleteUserRoleById(String recordId);

    /**
     * 根据id列表批量删除用户-角色信息
     * @param recordIds
     */
    void batchDeleteUserRoleByIds(List<String> recordIds);

    /**
     * 根据userId删除用户-角色信息
     * @param userId
     */
    void deleteUserRoleByUserId(String userId);

    /**
     * 保存模块信息
     * @param module
     */
    void saveModule(Module module);

    /**
     * 根据moduleId更新模块标识
     * @param moduleId
     * @param recordFlag
     */
    void updateModuleFlagById(String moduleId, String recordFlag);

    /**
     * 根据模块ID查询模块信息
     * @param moduleId
     * @return
     */
    Module findModuleById(String moduleId);

    /**
     * 批量保存角色-模块信息
     * @param roleModules
     */
    void batchSaveRoleModule(List<RoleModule> roleModules);

    /**
     * 根据id删除角色-模块信息
     * @param recordId
     */
    void deleteRoleModuleById(String recordId);

    /**
     * 根据id列表批量删除角色-模块信息
     * @param recordIds
     */
    void batchDeleteRoleModuleByIds(List<String> recordIds);

    /**
     * 根据角色id删除角色-模块信息
     * @param roleId
     */
    void deleteRoleModuleByRoleId(String roleId);

    /**
     * 根据用户id查询角色信息
     * @param userId 用户id
     * @return
     */
    List<Role> findRoleByUserId(String userId);

    /**
     * 根据角色名称查询角色信息
     * @param roleName
     * @return
     */
    List<Role> findRoleByRoleName(String roleName);

    /**
     * 更新角色信息
     * @param role
     */
    void updateRole(Role role);

    /**
     * 查询用户-角色-模块信息
     * @param userRoleModule
     * @return
     */
    List<UserRoleModule> findUserRoleModule(UserRoleModule userRoleModule);

    /**
     * 查询角色信息
     * @param role
     * @return
     */
    List<Role> findRole(Role role);

    /**
     * 查询模块信息
     * @param module
     * @return
     */
    List<Module> findModule(Module module);

    /**
     * 查询角色-模块信息
     * @param roleModule
     * @return
     */
    List<RoleModule> findRoleModule(RoleModule roleModule);

    /**
     * 查询用户-角色信息
     * @param userRole
     * @return
     */
    List<UserRole> findUserRole(UserRole userRole);

    /**
     * 更新模块信息
     * @param module
     */
    void updateModule(Module module);

    /**
     * 检索角色信息
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Role> searchRole(Role role, int pageNum, int pageSize);

    /**
     * 检索模块信息
     * @param module
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Module> searchModule(Module module, int pageNum, int pageSize);

    /**
     * 检索角色-模块信息
     * @param roleModule
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<RoleModule> searchRoleModule(RoleModule roleModule, int pageNum, int pageSize);

    /**
     * 检索用户-角色信息
     * @param userRole
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserRole> searchUserRole(UserRole userRole, int pageNum, int pageSize);

    /**
     * 检索用户-角色-模块信息
     * @param userRoleModule
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserRoleModule> searchUserRoleModule(UserRoleModule userRoleModule, int pageNum, int pageSize);


}