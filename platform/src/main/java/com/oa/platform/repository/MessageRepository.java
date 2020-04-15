package com.oa.platform.repository;

import com.oa.platform.entity.Message;
import org.springframework.stereotype.Repository;

/**
 * 消息数据处理
 * @author jianbo.feng
 * @create 2020/04/15
 */
@Repository
public interface MessageRepository extends BaseRepository<Message, String> {
}
