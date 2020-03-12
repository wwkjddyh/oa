package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.oa.platform.entity.*;
import com.oa.platform.repository.RoleRepository;
import com.oa.platform.service.RoleService;
import com.oa.platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author Feng
 * @date 2018/08/23
 */
@Service
public class RoleServiceImpl extends AbstractBaseService<Role, String> implements RoleService {

    public RoleServiceImpl() {
        super(Role.class);
    }

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    @Override
    public void saveRole(Role role) {
        roleRepository.insertRole(role);
    }

    @Transactional
    @Override
    public void updateRoleFlagById(String roleId, String recordFlag) {
        Map<String ,Object> map = Maps.newHashMap();
        map.put("roleId", roleId);
        map.put("recordFlag", recordFlag);
        roleRepository.updateRoleFlagById(map);
    }

    @Transactional
    @Override
    public void batchSaveUserRole(List<UserRole> userRoles) {
        roleRepository.batchInsertUserRole(userRoles);
    }

    @Transactional
    @Override
    public void deleteUserRoleById(String recordId) {
        roleRepository.deleteUserRoleById(recordId);
    }

    @Transactional
    @Override
    public void batchDeleteUserRoleByIds(List<String> recordIds) {
        roleRepository.batchDeleteUserRoleByIds(recordIds);
    }

    @Transactional
    @Override
    public void deleteUserRoleByUserId(String userId) {
        roleRepository.deleteUserRoleByUserId(userId);
    }

    @Transactional
    @Override
    public void saveModule(Module module) {
        roleRepository.insertModule(module);
    }

    @Transactional
    @Override
    public void updateModuleFlagById(String moduleId, String recordFlag) {
        roleRepository.updateModuleFlagById(new HashMap<String,Object>(){{
            put("moduleId", moduleId);
            put("recordFlag", recordFlag);
        }});
    }

    @Override
    public Module findModuleById(String moduleId) {
        return roleRepository.findModuleById(moduleId);
    }

    @Transactional
    @Override
    public void batchSaveRoleModule(List<RoleModule> roleModules) {
        roleRepository.batchInsertRoleModule(roleModules);
    }

    @Transactional
    @Override
    public void deleteRoleModuleById(String recordId) {
        roleRepository.deleteRoleModuleById(recordId);
    }

    @Transactional
    @Override
    public void batchDeleteRoleModuleByIds(List<String> recordIds) {
        roleRepository.batchDeleteRoleModuleByIds(recordIds);
    }

    @Transactional
    @Override
    public void deleteRoleModuleByRoleId(String roleId) {
        roleRepository.deleteRoleModuleByRoleId(roleId);
    }

    @Override
    public List<Role> findRoleByUserId(String userId) {
        List<Role> roles = new ArrayList<>(0);
        userId = StringUtil.trim(userId);
        if(!"".equals(userId)) {
            roles = roleRepository.findRoleByUserId(userId);
        }
        return roles;
    }

    @Override
    public List<Role> findRoleByRoleName(String roleName) {
        List<Role> roles = new ArrayList<>(0);
        roleName = StringUtil.trim(roleName);
        if(!"".equals(roleName)) {
            roles = roleRepository.findRoleByRoleName(roleName);
        }
        return roles;
    }

    @Transactional
    @Override
    public void updateRole(Role role) {
        if(role != null) {
            roleRepository.updateRole(role);
        }
    }

    @Override
    public List<UserRoleModule> findUserRoleModule(UserRoleModule userRoleModule) {
        return roleRepository.findUserRoleModule(userRoleModule);
    }

    @Override
    public List<Role> findRole(Role role) {
        return roleRepository.findRole(role);
    }

    @Override
    public List<Module> findModule(Module module) {
        return roleRepository.findModule(module);
    }

    @Override
    public List<RoleModule> findRoleModule(RoleModule roleModule) {
        return roleRepository.findRoleModule(roleModule);
    }

    @Override
    public List<UserRole> findUserRole(UserRole userRole) {
        return roleRepository.findUserRole(userRole);
    }

    @Override
    public PageInfo<Role> searchRole(Role role, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(roleRepository.findRole(role));
    }

    @Override
    public PageInfo<Module> searchModule(Module module, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(roleRepository.findModule(module));
    }

    @Override
    public PageInfo<RoleModule> searchRoleModule(RoleModule roleModule, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(roleRepository.findRoleModule(roleModule));
    }

    @Override
    public PageInfo<UserRole> searchUserRole(UserRole userRole, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(roleRepository.findUserRole(userRole));
    }

    @Override
    public PageInfo<UserRoleModule> searchUserRoleModule(UserRoleModule userRoleModule, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(roleRepository.findUserRoleModule(userRoleModule));
    }

    @Transactional
    @Override
    public void updateModule(Module module) {
        if(module != null) {
            roleRepository.updateModule(module);
        }
    }
}
