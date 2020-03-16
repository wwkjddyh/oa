package com.oa.platform.service.impl;

import com.oa.platform.entity.Article;
import com.oa.platform.service.ArticleService;
import org.springframework.stereotype.Service;

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
}
