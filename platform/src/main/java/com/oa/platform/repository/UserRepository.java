package com.oa.platform.repository;

import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * @author Feng
 * @date 2019/03/01
 */
@Repository
public interface UserRepository extends BaseRepository<User,String> {

    /**
     * 插入用户详细信息
     * @param userDtl 用户详细信息
     */
    void insertUserDtl(UserDtl userDtl);

    /**
     * 更新用户详细信息
     * @param userDtl
     */
    void updateUserDtl(UserDtl userDtl);

    List<User> selectUsers();

    /**
     * 根据用户ID查询用户详细信息
     * @param userId 用户id
     * @return
     */
    UserDtl findDetailByUserId(String userId);

    /**
     * 根据用户id列表获得用户详情
     * @param userIds
     * @return
     */
    List<UserDtl> findDetailByUserIds(List<String> userIds);

    /**
     * 根据用户名和密码查询用户信息
     * @param params {keys:userName、userPwd}
     * @return
     */
    User findUserByNameAndPwd(Map<String, Object> params);

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return
     */
    User findUserByName(String userName);
}