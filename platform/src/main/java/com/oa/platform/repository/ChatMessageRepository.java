package com.oa.platform.repository;

import com.oa.platform.entity.ChatMessage;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @create 2020/04/30
 */
@Repository
public interface ChatMessageRepository extends BaseRepository<ChatMessage, String> {
	
	/**
	 * 查询消息
	 * @param channelId
	 * @param maxCreateAt
	 * @param limit
	 * @return
	 */
	List<ChatMessage> listMessage(@Param("channelId") String channelId,@Param("maxCreateAt") long maxCreateAt,@Param("limit") int limit);
}
