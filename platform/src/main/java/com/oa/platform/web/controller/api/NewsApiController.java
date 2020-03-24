package com.oa.platform.web.controller.api;

import com.oa.platform.biz.NewsBiz;
import com.oa.platform.common.Constants;
import com.oa.platform.common.StatusCode;
import com.oa.platform.entity.News;
import com.oa.platform.util.StringUtil;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 消息接口
 * @author jianbo.feng
 * @date 2020/03/12
 */
@RestController
@RequestMapping("/api/news")
public class NewsApiController extends BaseController {

    @Autowired
    private NewsBiz newsBiz;

    /**
     * 保存信息
     * @param news 消息
     * @return
     */
    @PostMapping("save")
    public Map<String, Object> save(@RequestBody News news) {
        return newsBiz.save(news);
    }

    /**
     * 根据Id更新信息标识
     * @param id 信息ID
     * @param flag 信息标识
     * @return
     */
    @PostMapping("updateFlagById")
    public Map<String, Object> updateFlagById(@RequestParam String id, @RequestParam(value = "1") Integer flag) {
        return newsBiz.updateFlagById(id, flag);
    }

    /**
     * 根据ID更新删除
     * @param id 信息ID
     * @return
     */
    @PostMapping("deleteById")
    public Map<String, Object> deleteById(@RequestParam String id) {
        return newsBiz.deleteById(id);
    }

    /**
     * 根据ID获得信息
     * @param id 消息序号
     * @return
     */
    @GetMapping("get")
    public Map<String, Object> get(String id) {
        return newsBiz.get(id);
    }

    /**
     * 检索（模糊匹配名称、备注）
     * @param typeId 类型ID
     * @param isViewed 是否已查看(为null或""，则查询全部)
     * @param viewerId 查看者ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("search")
    public Map<String,Object> search(
            @RequestParam(defaultValue = "", required = false) String typeId,
            Integer isViewed,
            @RequestParam(defaultValue = "", required = false) String viewerId,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        return newsBiz.search(typeId, isViewed, viewerId, key, pageNum, pageSize);
    }

    /**
     * 获得当前用户消息（模糊匹配名称、备注）
     * @param isViewed 是否已查看(为null或""，则查询全部)
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("getCurrUserNews")
    public Map<String, Object> getCurrUserNews(
            Integer isViewed,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        String userId = this.getUserIdOfSecurity();
        if ("".equals(userId)) {
            return StringUtil.getResultVo(StatusCode.UNAUTHORIZED, "", "");
        }
        else {
            return newsBiz.searchSendRecord("", "", "",
                    userId, isViewed, key, Constants.INT_NORMAL, pageNum, pageSize);
        }
    }

    /**
     * 检索发送信息
     * @param id 发送信息ID
     * @param newsId 消息ID
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @param status 状态(0, 未查看; 1, 已查看)
     * @param key 关键字(模糊匹配消息标题、发送者姓名、接收者姓名)
     * @param flag 信息标识(1,正常;0,删除;)
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("searchSendRecord")
    public Map<String, Object> searchSendRecord(
            @RequestParam(defaultValue = "", required = false) String id,
            @RequestParam(defaultValue = "", required = false) String newsId,
            @RequestParam(defaultValue = "", required = false) String senderId,
            @RequestParam(defaultValue = "", required = false) String receiverId,
            Integer status,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = "1", required = false) Integer flag,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        return newsBiz.searchSendRecord(id, newsId, senderId, receiverId, status, key, flag, pageNum, pageSize);
    }

    /**
     * 邮件发送测试
     * @return
     */
    @GetMapping("mail")
    public Map<String, Object> sendMail() {
        return newsBiz.sendMail();
    }

    /**
     * 查看消息
     * @param recordId 接收信息记录(NewsSendRecord)的唯一标识
     * @return
     */
    @PostMapping("viewNews")
    public Map<String, Object> viewNews(@RequestParam String recordId) {
        return newsBiz.viewNews(recordId);
    }

    /**
     * 获得(当前)用户获得最新的消息通告
     * @return
     */
    @GetMapping("getCurrUserReceivedNewestNews")
    public Map<String, Object> getCurrUserReceivedNewestNews() {
        return newsBiz.getCurrUserReceivedNewestNews();
    }
}
