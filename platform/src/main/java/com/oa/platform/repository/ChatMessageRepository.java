package com.oa.platform.repository;

import com.oa.platform.entity.ChatMessage;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @create 2020/04/30
 */
@Repository
public interface ChatMessageRepository extends BaseRepository<ChatMessage, String> {
}
