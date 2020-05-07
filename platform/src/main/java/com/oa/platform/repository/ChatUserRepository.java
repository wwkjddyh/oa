package com.oa.platform.repository;

import com.oa.platform.entity.ChatUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends BaseRepository<ChatUser, String> {
	/**
	 * 根据名称或昵称查询用户
	 * @param name
	 * @return
	 */
	List listByNameOrNickname(@Param("name") String name);
}
