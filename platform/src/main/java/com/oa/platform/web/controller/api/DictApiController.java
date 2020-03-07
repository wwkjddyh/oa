package com.oa.platform.web.controller.api;

import com.oa.platform.biz.DictBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 字典API
 * @author Feng
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/api/dict")
public class DictApiController extends BaseController {

    @Autowired
    private DictBiz dictBiz;

    /**
     * 保存字典信息
     * @param dictId 字典ID
     * @param dictType 字典类型
     * @param dictName 字典名称
     * @param dictRemark 字典描述
     * @return Map&lt;String,Object&gt;
     */
    @PostMapping("saveDict")
    public Map<String,Object> saveDict(
            @RequestParam(defaultValue = "",required = false) String dictId,
            @RequestParam(defaultValue = "",required = false) String dictType,
            @RequestParam(defaultValue = "",required = false) String dictName,
            @RequestParam(defaultValue = "",required = false) String dictRemark) {
        return dictBiz.saveDict(dictId,dictType,dictName,dictRemark);
    }

    /**
     * 根据id修改标识
     * @param dictId 字典id
     * @param recordFlag 信息标识
     * @return Map&lt;String,Object&gt;
     */
    @PostMapping("updateDictFlagById")
    public Map<String,Object> updateDictFlagById(@RequestParam String dictId,
                                                 @RequestParam Integer recordFlag) {
        return dictBiz.updateDictFlagById(dictId,recordFlag);
    }

    /**
     * 根据id删除字典信息
     * @param dictId 字典id
     * @return Map&lt;String,Object&gt;
     */
    @PostMapping("deleteById")
    public Map<String,Object> deleteById(@RequestParam String dictId) {
        return dictBiz.deleteById(dictId);
    }

    /**
     * 检索（模糊匹配名称、备注）
     * @param dictType 字典类型
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return Map&lt;String,Object&gt;
     */
    @GetMapping("search")
    public Map<String,Object> search(@RequestParam(defaultValue = "",required = false) String dictType,
                                     @RequestParam(defaultValue = "",required = false) String key,
                                     @RequestParam(defaultValue = "1",required = false) int pageNum,
                                     @RequestParam(defaultValue = "10",required = false) int pageSize) {
        return dictBiz.search(dictType,key,pageNum,pageSize);
    }
}
