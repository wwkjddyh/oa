package com.oa.platform.repository;

import com.oa.platform.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author Feng
 * @date 2019/03/01
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, String> {

    /**
     * 插入角色信息
     * @param role
     */
    void insertRole(Role role);

    /**
     * 根据Id更新信息标识
     * @param param
     */
    void updateRoleFlagById(Map<String, Object> param);

    /**
     * 批量插入用户-角色信息
     * @param userRoles
     */
    void batchInsertUserRole(List<UserRole> userRoles);

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
     * 插入模块信息
     * @param module
     */
    void insertModule(Module module);

    /**
     * 根据moduleId更新模块标识
     * @param param
     */
    void updateModuleFlagById(Map<String, Object> param);

    /**
     * 根据模块ID查询模块信息
     * @param moduleId
     * @return
     */
    Module findModuleById(String moduleId);

    /**
     * 批量插入角色-模块信息
     * @param roleModules
     */
    void batchInsertRoleModule(List<RoleModule> roleModules);

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
     * 根据ID列表查询角色信息
     * @param roleIds ID列表
     * @return
     */
    List<Role> findRoleByIds(List<String> roleIds);

    /**
     * 根据用户ID查询模块信息
     * @param userId 用户ID
     * @return
     */
    List<Module> findModuleByUserId(String userId);
}
