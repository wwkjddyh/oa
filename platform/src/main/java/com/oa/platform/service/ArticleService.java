package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Article;
import com.oa.platform.entity.BriefSendRecord;

import java.util.List;

/**
 * 文章
 * @author Feng
 * @date 2020/03/16
 */
public interface ArticleService extends BaseService<Article,String> {

    /**
     * 查询简报发送记录
     * @param briefSendRecord 简报发送记录
     * @return
     */
    List<BriefSendRecord> findBriefSendRecord(BriefSendRecord briefSendRecord);

    /**
     * 批量更新简报发送记录
     * @param ids 记录ID列表
     */
    void batchUpdateBriefSendRecordFlagByIds(List<String> ids);

    /**
     * 批量删除简报发送记录
     * @param ids 记录ID列表
     */
    void batchDeleteBriefSendRecordByIds(List<String> ids);

    /**
     * 删除简报发送记录
     * @param briefSendRecord 简报发送记录信息
     */
    void deleteBriefSendRecord(BriefSendRecord briefSendRecord);

    /**
     * 更新简报发送记录
     * @param briefSendRecord 简报发送记录信息
     */
    void updateBriefSendRecord(BriefSendRecord briefSendRecord);

    /**
     * 批量保存简报发送记录
     * @param records 发送记录列表
     */
    void batchSaveBriefSendRecord(List<BriefSendRecord> records);

    /**
     * 保存简报发送记录
     * @param briefSendRecord 发送记录
     */
    void saveBriefSendRecord(BriefSendRecord briefSendRecord);

    /**
     * 根据简报ID查询接收者ID列表
     * @param briefId 简报ID
     * @return
     */
    List<String> findReceiverIdByBriefId(String briefId);

    /**
     * 检索简报发送记录消息
     * @param record 发送记录参数
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<BriefSendRecord> searchBriefSendRecord(BriefSendRecord record, int pageNum, int pageSize);

	void deleteArticleById(String recordId,String userId);

	PageInfo<BriefSendRecord> searchBriefSendRecordBySendId(BriefSendRecord record, int pageNum, int pageSize);
}
