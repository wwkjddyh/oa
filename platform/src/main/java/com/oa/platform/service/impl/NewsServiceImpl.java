package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.News;
import com.oa.platform.entity.NewsSendRecord;
import com.oa.platform.repository.NewsRepository;
import com.oa.platform.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息
 * @author jianbo.feng
 * @date 2020/03/12
 */
@Service
public class NewsServiceImpl extends AbstractBaseService<News, String> implements NewsService {

    public NewsServiceImpl() {
        super(News.class);
    }

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public void saveNewsSendRecord(NewsSendRecord record) {
        newsRepository.insertNewsSendRecord(record);
    }

    @Override
    public void deleteNewsSendRecord(NewsSendRecord record) {
        newsRepository.deleteNewsSendRecord(record);
    }

    @Override
    public void batchSaveNewsSendRecord(List<NewsSendRecord> records) {
        newsRepository.batchInsertNewsSendRecord(records);
    }

    @Override
    public List<NewsSendRecord> findNewsSendRecord(NewsSendRecord record) {
        return newsRepository.findNewsSendRecord(record);
    }

    @Override
    public PageInfo<NewsSendRecord> searchNewsSendRecord(NewsSendRecord record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<NewsSendRecord> records = newsRepository.findNewsSendRecord(record == null ? new NewsSendRecord() : record);
        return new PageInfo<>(records);
    }

    @Override
    public void updateNewsSendRecord(NewsSendRecord record) {
        newsRepository.updateNewsSendRecord(record);
    }

    @Override
    public void batchDeleteNewsSendRecordByIds(List<String> ids) {
        newsRepository.batchDeleteNewsSendRecordByIds(ids);
    }

    @Override
    public void batchUpdateNewsSendRecordFlagByIds(List<String> ids) {
        newsRepository.batchUpdateNewsSendRecordFlagByIds(ids);
    }
}
