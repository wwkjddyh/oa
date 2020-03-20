package com.oa.platform.web.controller.api;

import com.oa.platform.biz.ResBiz;
import com.oa.platform.entity.Res;
import com.oa.platform.entity.ResDl;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 资源API接口
 * @author Feng
 * @date 2020/03/15
 */
@RestController
@RequestMapping("/api/res")
public class ResApiController extends BaseController {

    @Autowired
    private ResBiz resBiz;

    /**
     * 保存或更新资源
     * @param res 资源信息
     * @return
     */
    @PostMapping("save")
    public Map<String, Object> save(@RequestBody Res res) {
        return resBiz.save(res);
    }

    /**
     * 保存下载信息
     * @param resDl 下载信息
     * @return
     */
    @PostMapping("saveResDl")
    public Map<String, Object> saveResDl(@RequestBody ResDl resDl) {
        return resBiz.saveResDl(resDl);
    }

    /**
     * 根据resId获得资源信息
     * @param resId 唯一标识
     * @return
     */
    @GetMapping("get")
    public Map<String, Object> get(@RequestParam String resId) {
        return resBiz.get(resId);
    }

    /**
     * 根据resId删除信息
     * @param resId 唯一标识
     * @return
     */
    @PostMapping("deleteById")
    public Map<String, Object> deleteById(@RequestParam String resId) {
        return resBiz.deleteById(resId);
    }

    /**
     * 根据resDlId删除资源下载信息
     * @param resDlId 唯一标识
     * @return
     */
    @PostMapping("deleteByResDlId")
    public Map<String, Object> deleteByResDlId(String resDlId) {
        return resBiz.deleteByResDlId(resDlId);
    }

    /**
     * 检索资源
     * @param typeId 分类ID
     * @param assId 关联信息ID
     * @param assTypeId 关联类型ID
     * @param announcerId 发布者ID
     * @param editorId 编辑者ID
     * @param orgId 组织ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("search")
    public Map<String, Object> search(
            @RequestParam(defaultValue = "", required = false) String typeId,
            @RequestParam(defaultValue = "", required = false) String assId,
            @RequestParam(defaultValue = "", required = false) String assTypeId,
            @RequestParam(defaultValue = "", required = false) String announcerId,
            @RequestParam(defaultValue = "", required = false) String editorId,
            @RequestParam(defaultValue = "", required = false) String orgId,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        return resBiz.search(typeId, assId, assTypeId, announcerId, editorId, orgId, key, pageNum, pageSize);
    }

    /**
     * 检索资源下载信息
     * @param recordId 唯一标识
     * @param resId 资源ID
     * @param userId 下载用户ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("searchDl")
    public Map<String, Object> searchDl(
            @RequestParam(defaultValue = "", required = false) String recordId,
            @RequestParam(defaultValue = "", required = false) String resId,
            @RequestParam(defaultValue = "", required = false) String userId,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false) int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        return resBiz.searchDl(recordId, resId, userId, key, pageNum, pageSize);
    }

    /**
     * 更新资源附件信息
     * @param recordId 信息标识
     * @param originalName 原始文件名
     * @param currName 当前文件名
     * @param accessUrl 访问路径
     * @param resSize 资源大小
     * @return
     */
    @PostMapping("uploadAttachmentInfo")
    public Map<String, Object> uploadAttachmentInfo(
            @RequestParam String recordId,
            @RequestParam String originalName,
            @RequestParam String currName,
            @RequestParam String accessUrl,
            @RequestParam(defaultValue = "0", required = false) String resSize) {
        return resBiz.uploadAttachmentInfo(recordId, originalName, currName, accessUrl, resSize);
    }
}
