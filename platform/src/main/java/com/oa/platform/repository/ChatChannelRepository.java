package com.oa.platform.repository;

import com.oa.platform.entity.ChannelDTO;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @create 2020/04/30
 */
@Repository
public interface ChatChannelRepository extends BaseRepository<ChannelDTO, String> {
}
