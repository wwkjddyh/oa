package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.News;
import com.oa.platform.service.NewsService;
import com.oa.platform.service.RoleService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 消息业务处理
 * @author jianbo.feng
 * @date 2020/03/12
 */
@Component
public class NewsBiz extends BaseBiz {

    @Autowired
    private NewsService newsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 保存信息
     * @param news 消息
     * @return
     */
    public Map<String, Object> save(News news) {
        ret = null;
        if (news == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                String recordId = StringUtil.trim(news.getRecordId());
                if ("".equals(recordId)) {
                    newsService.save(news);
                }
                else {
                    newsService.update(news);
                }
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据Id更新信息标识
     * @param recordId 信息ID
     * @param recordFlag 信息标识
     * @return
     */
    public Map<String, Object> updateFlagById(String recordId, Integer recordFlag) {
        ret = null;
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId) || recordFlag == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                News news = new News();
                news.setRecordFlag(recordFlag);
                news.setRecordId(recordId);
                newsService.update(news);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据ID更新删除
     * @param recordId 信息ID
     * @return
     */
    public Map<String, Object> deleteById(String recordId) {
        ret = null;
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                News news = new News();
                news.setRecordId(recordId);
                newsService.delete(news);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据ID获得信息
     * @param recordId 消息序号
     * @return
     */
    public Map<String, Object> get(String recordId) {
        ret = null;
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                News news = newsService.getById(recordId);
                String receiverId = StringUtil.trim(news.getReceiverId());
                if (!"".equals(receiverId)) {
                    List<String> ids = Arrays.asList(receiverId.split(","));
                    Integer receiverType = news.getReceiverType();
                    if (receiverType != null) {
                        if (receiverType == Constants.RECEIVER_TYPE_USER) {
                            news.setReceiveUsers(userService.findByIds(ids));
                        }
                        else if(receiverType == Constants.RECEIVER_TYPE_ROLE) {
                            news.setReceiveRoles(roleService.findRoleByIds(ids));
                        }
                    }
                }
                ret = this.getSuccessVo("", news);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }

        return ret;
    }

    /**
     * 检索（模糊匹配名称、备注）
     * @param typeId 类型ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> search(String typeId, String key, int pageNum, int pageSize) {
        ret = null;
        try {
            News news = new News();
            news.setTypeId(StringUtil.trim(typeId));
            news.setKey(StringUtil.trim(key));
            news.setRecordFlag(Constants.INT_NORMAL);
            PageInfo<News> pageInfo = newsService.search(news, pageNum, pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
