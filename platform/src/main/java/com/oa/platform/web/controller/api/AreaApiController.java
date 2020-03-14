package com.oa.platform.web.controller.api;

import com.oa.platform.biz.AreaBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 地域API接口
 * @author Feng
 * @date 2020/03/14
 */
@RestController
@RequestMapping("/api/area")
public class AreaApiController extends BaseController {

    @Autowired
    private AreaBiz areaBiz;

    /**
     * 保存或更新
     * @param areaId 信息唯一标识
     * @param areaName 名称
     * @param areaDesc 描述
     * @param parentId 父级id(为null或"")
     * @return
     */
    @PostMapping("save")
    public Map<String, Object> save(
            @RequestParam(defaultValue = "", required = false) String areaId,
            @RequestParam String areaName,
            @RequestParam(defaultValue = "", required = false) String areaDesc,
            @RequestParam(defaultValue = "", required = false) String parentId) {
        return areaBiz.save(areaId, areaName, areaDesc, parentId);
    }

    /**
     * 根据areaId获得地域信息
     * @param areaId 唯一标识
     * @return
     */
    @GetMapping("get")
    public Map<String, Object> get(@RequestParam String areaId) {
        return areaBiz.get(areaId);
    }

    /**
     * 根据areaId删除信息
     * @param areaId 唯一标识
     * @return
     */
    @PostMapping("deleteById")
    public Map<String, Object> deleteById(@RequestParam String areaId) {
        return areaBiz.deleteById(areaId);
    }

    /**
     * 检索
     * @param parentId 父级ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("search")
    public Map<String, Object> search(
            @RequestParam(defaultValue = "", required = false) String parentId,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        return areaBiz.search(parentId, key, pageNum, pageSize);
    }
}
