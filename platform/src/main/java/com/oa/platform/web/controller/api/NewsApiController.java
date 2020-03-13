package com.oa.platform.web.controller.api;

import com.oa.platform.biz.NewsBiz;
import com.oa.platform.entity.News;
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
    public Map<String, Object> save(@RequestParam News news) {
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
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("search")
    public Map<String,Object> search(
            @RequestParam(defaultValue = "",required = false) String typeId,
            @RequestParam(defaultValue = "",required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return newsBiz.search(typeId, key, pageNum, pageSize);
    }
}
