package com.oa.platform.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oa.platform.entity.Article;
import com.oa.platform.entity.BriefSendRecord;

/**
 * 文章信息
 * @author Feng
 * @date 2020/03/16
 */
@Repository
public interface ArticleRepository extends BaseRepository<Article, String> {

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
     * 批量插入简报发送记录
     * @param records 发送记录列表
     */
    void batchInsertBriefSendRecord(List<BriefSendRecord> records);

    /**
     * 插入简报发送记录
     * @param briefSendRecord 发送记录
     */
    void insertBriefSendRecord(BriefSendRecord briefSendRecord);

    /**
     * 根据简报ID查询接收者ID列表
     * @param briefId 简报ID
     * @return
     */
    List<String> findReceiverIdByBriefId(String briefId);

	void deleteArticleById(@Param("recordId") String recordId,@Param("userId") String userId);

	List<BriefSendRecord> searchBriefSendRecordBySendId(BriefSendRecord briefSendRecord);

	List<BriefSendRecord> searchApproveXXJLRecord(@Param("userId") String userId, @Param("status") Integer status,@Param("categoryId") String categoryId);

	void updateRecordStatus(String briefId, int status);

	void updateArtcleUser(@Param("briefId") String briefId, @Param("userId") String userId, @Param("status") int status);
}
