package com.oa.platform.repository;

import com.oa.platform.entity.News;
import com.oa.platform.entity.NewsSendRecord;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息
 * @author jianbo.feng
 * @date 2020/03/12
 */
@Repository
public interface NewsRepository extends BaseRepository<News, String> {

    /**
     * 插入消息发送记录
     * @param record 发送记录信息
     */
    void insertNewsSendRecord(NewsSendRecord record);

    /**
     * 删除消息发送记录
     * @param record 发送记录信息
     */
    void deleteNewsSendRecord(NewsSendRecord record);

    /**
     * 批量插入消息发送记录
     * @param records 发送记录信息列表
     */
    void batchInsertNewsSendRecord(List<NewsSendRecord> records);

    /**
     * 查询消息发送记录
     * @param record 发送记录消息参数
     * @return
     */
    List<NewsSendRecord> findNewsSendRecord(NewsSendRecord record);

    /**
     * 更新消息发送信息
     * @param record 发送记录信息
     */
    void updateNewsSendRecord(NewsSendRecord record);

    /**
     * 根据ID组删除消息发送记录(物理删除)
     * @param ids 记录ID组
     */
    void batchDeleteNewsSendRecordByIds(List<String> ids);

    /**
     * 根据ID组更新消息发送记录标识(逻辑删除)
     * @param ids 记录ID组
     */
    void batchUpdateNewsSendRecordFlagByIds(List<String> ids);

    /**
     * 获得用户最新的消息
     * @param userId 用户ID
     * @return
     */
    List<News> getUserReceivedNewestNews(String userId);
    
    List<String> getMailByUserIds(@Param("userId") String userId);
}
