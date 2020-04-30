package com.oa.platform.repository;

import com.oa.platform.entity.UserDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends BaseRepository<UserDTO, String> {
}
