package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oa.platform.entity.Article;
import com.oa.platform.entity.BriefSendRecord;
import com.oa.platform.repository.ArticleRepository;
import com.oa.platform.service.ArticleService;
import com.oa.platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章信息
 * @author Feng
 * @date 2020/03/16
 */
@Service
public class ArticleServiceImpl extends AbstractBaseService<Article,String> implements ArticleService {

    public ArticleServiceImpl() {
        super(Article.class);
    }

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<BriefSendRecord> findBriefSendRecord(BriefSendRecord briefSendRecord) {
        return articleRepository.findBriefSendRecord(briefSendRecord);
    }

    @Override
    public void batchUpdateBriefSendRecordFlagByIds(List<String> ids) {
        articleRepository.batchUpdateBriefSendRecordFlagByIds(ids);
    }

    @Override
    public void batchDeleteBriefSendRecordByIds(List<String> ids) {
        articleRepository.batchDeleteBriefSendRecordByIds(ids);
    }

    @Override
    public void deleteBriefSendRecord(BriefSendRecord briefSendRecord) {
        articleRepository.deleteBriefSendRecord(briefSendRecord);
    }

    @Override
    public void updateBriefSendRecord(BriefSendRecord briefSendRecord) {
        articleRepository.updateBriefSendRecord(briefSendRecord);
    }

    @Override
    public void batchSaveBriefSendRecord(List<BriefSendRecord> records) {
        articleRepository.batchInsertBriefSendRecord(records);
    }

    @Override
    public void saveBriefSendRecord(BriefSendRecord briefSendRecord) {
        articleRepository.insertBriefSendRecord(briefSendRecord);
    }

    @Override
    public List<String> findReceiverIdByBriefId(String briefId) {
        briefId = StringUtil.trim(briefId);
        if ("".equals(briefId)) {
            return Lists.newArrayList();
        }
        return articleRepository.findReceiverIdByBriefId(briefId);
    }

    @Override
    public PageInfo<BriefSendRecord> searchBriefSendRecord(BriefSendRecord record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BriefSendRecord> records = articleRepository.findBriefSendRecord(record == null ? new BriefSendRecord() : record);
        return new PageInfo<>(records);
    }
}
