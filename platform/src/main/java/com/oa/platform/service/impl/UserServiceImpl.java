package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Module;
import com.oa.platform.entity.Organization;
import com.oa.platform.entity.Role;
import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.repository.OrgRepository;
import com.oa.platform.repository.RoleRepository;
import com.oa.platform.repository.UserRepository;
import com.oa.platform.service.UserService;
import com.oa.platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * 用户
 * @author Feng
 * @date 2018/08/23
 */
@Service(value = "userService")
public class UserServiceImpl extends AbstractBaseService<User,String> implements UserService {

    @Autowired
    private UserRepository userRepository;//这里会报错，但是并不会影响
    @Autowired
	private OrgRepository orgRepository;
    @Autowired
    private RoleRepository roleRepository;

    UserServiceImpl() {
        super(User.class);
    }


    /*@Override
    public int addUser(User user) {

        return userDao.insert(user);
    }*/

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public PageInfo<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(userRepository.selectUsers());
    }
    @Override
	public PageInfo<User> searchUsersByOrgIds(User user, int pageNum, int pageSize, boolean isSuperAdmin, List<String> orgIds) {
    	PageHelper.startPage(pageNum, pageSize);
		return new PageInfo(userRepository.searchUsersByOrgIds(user,isSuperAdmin,orgIds));
	}
    @Override
    public UserDtl findDetailByUserId(String userId) {
        return userRepository.findDetailByUserId(userId);
    }

    @Transactional
    @Override
    public void saveUserDtl(UserDtl userDtl) {
        userRepository.insertUserDtl(userDtl);
    }

    @Transactional
    @Override
    public void updateUserDtl(UserDtl userDtl) {
        userRepository.updateUserDtl(userDtl);
    }

    @Override
    public User findUserByNameAndPwd(final String userName,final String userPwd) {
        User user = null;
        if(!"".equals(userName) && !"".equals(userPwd)) {
            user = userRepository.findUserByNameAndPwd(new HashMap<String,Object>(){{
                put("userName",userName);
                put("userPwd", userPwd);
            }});
            if(user != null) {
                String userId = user.getUserId();
                user.setAuthorities(findRoleByUserId(userId));
            }
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findUserByName(s);
    }

    @Override
    public User findUserByName(String userName) {
        User user = null;
        userName = StringUtil.trim(userName);
        if(!"".equals(userName)) {
            user = userRepository.findUserByName(userName);
            if(user != null) {
                String userId = user.getUserId();
                user.setAuthorities(findRoleByUserId(userId));
                user.setUserDtl(findDetailByUserId(userId));
                Map<String, Object> param = Maps.newHashMap();
                param.put("userId", userId);
                param.put("isMenu", null);
                List<Module> modules = roleRepository.findModuleByUserId(param);
                List<Module> menus = Lists.newArrayList();
                if (!modules.isEmpty()) {
                    for (Module module : modules) {
                        if (module.getIsMenu() == Constants.IS_MENU) {
                            menus.add(module);
                        }
                    }
                }
                user.setModules(modules);
                user.setMenus(menus);
            }
        }
        return user;
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
    public List<UserDtl> findDetailByUserIds(List<String> userIds) {
        List<UserDtl> userDtls = new ArrayList<>();
        if (userIds != null && !userIds.isEmpty()) {
            userDtls = userRepository.findDetailByUserIds(userIds);
        }
        return userDtls;
    }

    @Override
    public Map<String, User> findByUserIds(List<String> userIds) {
        Map<String, User> ret = new HashMap<>();
        if (userIds != null && !userIds.isEmpty()) {
            User user = new User();
            user.setIds(userIds);
            user.setRecordFlag(Constants.INT_NORMAL);
            ret = find(user)
                    .parallelStream()
                    .collect(toMap(User::getUserId, Function.identity(), (key1, key2) -> key1));
        }
        return ret;
    }

    @Override
    public Map<String, UserDtl> findDtlsByUserIds(List<String> userIds) {
        Map<String, UserDtl> ret = new HashMap<>();
        if (userIds != null && !userIds.isEmpty()) {
            ret = findDetailByUserIds(userIds)
                    .parallelStream()
                    .collect(toMap(UserDtl::getUserId, Function.identity(), (key1, key2) -> key1));
        }
        return ret;
    }

    @Override
    public List<User> findByIds(List<String> ids) {
        return userRepository.findByIds(ids);
    }

    @Override
    public Map<String, User> listToMap(List<User> users) {
        Map<String, User> ret = Maps.newHashMap();
        if (users != null && !users.isEmpty()) {
            users.parallelStream().forEach(e -> ret.put(e.getUserId(), e));
        }
        return ret;
    }

    @Override
    public Map<String, String> findUserNamesByIds(List<String> ids) {
        Map<String, String> ret = Maps.newHashMap();
        if (ids != null && !ids.isEmpty()) {
            List<User> users = findByIds(ids);
            if (users != null && !users.isEmpty()) {
                users.parallelStream().forEach(e -> ret.put(e.getUserId(), e.getUserName()));
            }
        }
        return ret;
    }


	@Override
	public void resetPwd(String userId, String userDefaultPwd) {
		userRepository.resetPwd(userId,userDefaultPwd);
		
	}


	@Override
	public List<String> getUsersByCurrentUser(String userId) {
		User user = new User();
        user.setUserType(User.TYPE_ADMIN);
        user.setRecordFlag(Constants.INT_NORMAL);
        List<String> orgIds = new ArrayList<String>();
        
        List<Organization> orgIdByuserId = orgRepository.getOrgIdByuserId(userId);
		if(orgIdByuserId == null || orgIdByuserId.size() == 0) {
			return null;
		}
		List<Organization> result = orgRepository.getUserUpperOrgList(orgIdByuserId.get(0).getOrgId());
		for (Organization organization : result) {
			orgIds.add(organization.getOrgId());
		}
        
        List<User> searchUsersByOrgIds = userRepository.searchUsersByOrgIds(user,false,orgIds);
        List<String> userIds = new ArrayList<String>();
        for (User user2 : searchUsersByOrgIds) {
        	userIds.add(user2.getUserId());
		}
		return userIds;
	}


	@Override
	public List<String> getOrgIdsByCurrentUser(String userId) {
		List<String> orgIds = new ArrayList<String>();
		List<Organization> orgIdByuserId = orgRepository.getOrgIdByuserId(userId);
		if(orgIdByuserId == null || orgIdByuserId.size() == 0) {
			return new ArrayList<String>();
		}
		List<Organization> result = orgRepository.getUserUpperOrgList(orgIdByuserId.get(0).getOrgId());
		for (Organization organization : result) {
			orgIds.add(organization.getOrgId());
		}
		return orgIds;
	}


	@Override
	public void updateUserEmailAndPhone(String userId, String mail,String phone) {
		userRepository.updateUserEmailAndPhone(userId,mail,phone);
		
	}


	@Override
	public List<String> getuserIdByUser(String userName) {
		return userRepository.getuserIdByUser(userName);
	}


	
}
