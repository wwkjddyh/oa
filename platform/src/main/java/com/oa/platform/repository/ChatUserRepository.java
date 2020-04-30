package com.oa.platform.repository;

import com.oa.platform.entity.ChatUser;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends BaseRepository<ChatUser, String> {
}
