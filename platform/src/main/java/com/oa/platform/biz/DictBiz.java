package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Dict;
import com.oa.platform.service.DictService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 字典信息
 * @author Feng
 * @date 2019/03/01
 */
@Component
public class DictBiz extends BaseBiz {

    @Autowired
    private DictService dictService;

    /**
     * 是否重复
     * @param dictId 字典ID
     * @param dictType 字典类型
     * @param dictName 字典名称
     * @return
     */
    boolean validRepeat(String dictId, String dictType, String dictName) {
        boolean isRepeat = false;
        dictId = StringUtil.trim(dictId);
        dictType = StringUtil.trim(dictType);
        dictName = StringUtil.trim(dictName);
        Dict dict = new Dict();
        dict.setDictType(dictType);
        dict.setDictName(dictName);
        dict.setRecordFlag(Constants.INT_NORMAL);
        List<Dict> dictList = dictService.find(dict);
        if(dictList != null && !dictList.isEmpty()) {
            if("".equals(dictId)) {
                isRepeat = true;
            }
            else {
                final String finalDictId = dictId;
                isRepeat = dictList.parallelStream().anyMatch(e -> !e.getDictId().equals(finalDictId));
            }
        }
        return isRepeat;
    }

    /**
     * 保存字典信息
     * @param dictId 字典唯一标识
     * @param dictType 字典类型
     * @param dictName 字典名称
     * @param dictRemark 字典描述
     * @return
     */
    public Map<String,Object> saveDict(String dictId, String dictType,String dictName,String dictRemark) {
        ret = null;
        dictId = StringUtil.trim(dictId);
        dictType = StringUtil.trim(dictType);
        dictName = StringUtil.trim(dictName);
        if("".equals(dictType) || "".equals(dictType)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                boolean isRepeat = validRepeat(dictId, dictType, dictName);
                if (isRepeat) {
                    ret = this.getParamRepeatErrorVo("字典名称");
                } else {
                    Dict dict = new Dict();
                    dict.setDictName(dictName);
                    dict.setDictType(dictType);
                    dict.setDictRemark(StringUtil.trim(dictRemark));
                    dict.setRecordFlag(Constants.INT_NORMAL);
                    boolean isEdit = false;
                    if (!"".equals(dictId)) {
                        dict.setDictId(dictId);
                        isEdit = true;
                    } else {
                        dict.setDictId(StringUtil.getRandomUUID());
                    }

                    if (isEdit) {
                        dictService.update(dict);
                    } else {
                        dictService.save(dict);
                    }
                    ret = this.getSuccessVo("", "");
                }
            }
            catch(Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
            ret = this.getSuccessVo("","");
        }
        return ret;
    }

    /**
     * 根据id修改标识
     * @param dictId 字典id
     * @param recordFlag 记录标识
     */
    public Map<String,Object> updateDictFlagById(String dictId, Integer recordFlag) {
        ret = null;
        dictId = StringUtil.trim(dictId);
        if("".equals(dictId) || recordFlag == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
               dictService.updateDictFlagById(dictId,recordFlag);
               ret = this.getSuccessVo("","");
            }
            catch(Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据id删除字典信息
     * @param dictId
     * @return
     */
    public Map<String,Object> deleteById(String dictId) {
        ret = null;
        dictId = StringUtil.trim(dictId);
        if("".equals(dictId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Dict dict = new Dict();
                dict.setDictId(dictId);
                dictService.delete(dict);
                ret = this.getSuccessVo("","");
            }
            catch(Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 检索（模糊匹配名称、备注）
     * @param dictType 字典类型
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> search(String dictType,String key,int pageNum,int pageSize) {
        ret = null;
        try {
            Dict dict = new Dict();
            dict.setDictType(StringUtil.trim(dictType));
            dict.setKey(StringUtil.trim(key));
            dict.setRecordFlag(Constants.INT_NORMAL);
            PageInfo<Dict> pageInfo = dictService.search(dict,pageNum,pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 根据字典类型查询
     * @param dictType 字典类型(若为空，则查询所有)
     * @return
     */
    public Map<String, Object> getByType(String dictType) {
        dictType = StringUtil.trim(dictType);
        try {
            Dict dict = new Dict();
            dict.setDictType(dictType);
            dict.setRecordFlag(Constants.INT_NORMAL);
            ret = this.getSuccessVo("", dictService.find(dict));
        } catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 获取年月信息
     * @return
     */
    public Map<String, Object> getYearMonths() {
        try {
            Date currDate = new Date();
            Date minDate = DateUtil.addYear(currDate, -5);
            Date maxDate = DateUtil.addYear(currDate, 2);
            List<Map<String, String>> yearMonths = DateUtil.getMonthBetweens(minDate, maxDate);
            ret = this.getSuccessVo("", yearMonths);
        } catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
