package com.oa.platform.repository;

import com.oa.platform.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章信息
 * @author Feng
 * @date 2020/03/16
 */
@Repository
public interface ArticleRepository extends BaseRepository<Article, String> {
}
