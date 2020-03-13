package com.oa.platform.repository;

import com.oa.platform.entity.News;
import org.springframework.stereotype.Repository;

/**
 * 消息
 * @author jianbo.feng
 * @date 2020/03/12
 */
@Repository
public interface NewsRepository extends BaseRepository<News, String> {
}
