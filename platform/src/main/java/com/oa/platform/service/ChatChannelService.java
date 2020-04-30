package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.ChatChannel;
import com.oa.platform.entity.ChatChannelList;
import com.oa.platform.entity.ChatChannelMember;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 频道管理服务接口
 * 
 * @author Leo
 * @date 2018/4/11
 */
@Service
public interface ChatChannelService extends BaseService<ChatChannel, String> {

    /**
     * 得到频道列表
     * @param parameters
     * @param limit
     * @return
     */
    List<ChatChannelList> listChannel(Map<String, Object> parameters, int limit);

    /**
     * 得到群组频道列表
     * @param parameters
     * @param limit
     * @return
     */
    List<ChatChannelList> listGroupChannel(Map<String, Object> parameters, int limit);

    /**
     * 添加频道
     * @param dto
     * @param creatorNickname
     * @return
     */

    ChatChannel saveChannel(ChatChannel dto, String creatorNickname);

    /**
     * 得到用户是否为频道的管理员
     * @param userId
     * @param channelId
     * @return
     */
    boolean isAdmin(String userId, String channelId);

    /**
     * 更新频道名称
     * @param channelId
     * @param name
     * @return
     */

    int updateName(String channelId, String name);

    /**
     * 更新频道用途
     * @param channelId
     * @param purpose
     * @return
     */

    int updatePurpose(String channelId, String purpose);

    /**
     * 添加频道成员
     * @param channelId
     * @param userIds
     * @param userNicknames
     * @param admin
     * @return
     */

    int addMember(String channelId, String[] userIds, String[] userNicknames, String admin);

    /**
     * 移除组成员
     * @param channelId
     * @param memberId
     * @param memberNickname
     * @param admin
     * @return
     */

    int removeMember(String channelId, String memberId, String memberNickname, String admin);

    /**
     * 得到成员列表
     * @param channelId
     * @param username
     * @param limit
     * @param offset
     * @return
     */
    PageInfo<ChatChannelMember> listMember(String channelId, String username, int limit, int offset);

    /**
     * 变更频道管理员
     * @param channelId
     * @param memberId
     * @param isAdmin
     * @return
     */

    int changeAdmin(String channelId, String memberId, boolean isAdmin);

    /**
     * 离开频道
     * @param channelId
     * @param memberId
     * @param memberNickname
     * @return
     */

    int leaveChannel(String channelId, String memberId, String memberNickname);

    /**
     * 删除频道
     * @param channelId
     * @param adminId
     * @return
     */

    int removeChannel(String channelId, String adminId);

}
