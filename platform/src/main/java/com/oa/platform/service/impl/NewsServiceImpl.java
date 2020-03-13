package com.oa.platform.service.impl;

import com.oa.platform.entity.News;
import com.oa.platform.repository.NewsRepository;
import com.oa.platform.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}
