package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.News;
import com.oa.platform.entity.NewsSendRecord;

import java.util.List;

/**
 * 消息
 * @author jianbo.feng
 * @date 2020/03/12
 */
public interface NewsService extends BaseService<News, String> {

    /**
     * 插入消息发送记录
     * @param record 发送记录信息
     */
    void saveNewsSendRecord(NewsSendRecord record);

    /**
     * 删除消息发送记录
     * @param record 发送记录信息
     */
    void deleteNewsSendRecord(NewsSendRecord record);

    /**
     * 批量保存消息发送记录
     * @param records 发送记录信息列表
     */
    void batchSaveNewsSendRecord(List<NewsSendRecord> records);

    /**
     * 更新消息发送信息
     * @param record 发送记录信息
     */
    void updateNewsSendRecord(NewsSendRecord record);

    /**
     * 查询消息发送记录
     * @param record 发送记录消息参数
     * @return
     */
    List<NewsSendRecord> findNewsSendRecord(NewsSendRecord record);

    /**
     * 检索发送记录消息
     * @param record 发送记录参数
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<NewsSendRecord> searchNewsSendRecord(NewsSendRecord record, int pageNum, int pageSize);

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
    News getUserReceivedNewestNews(String userId);
}
