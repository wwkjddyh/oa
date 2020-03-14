package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Area;
import com.oa.platform.service.AreaService;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 地域信息业务处理
 */
@Component
public class AreaBiz extends BaseBiz {

    @Autowired
    private AreaService areaService;

    /**
     * 是否重复
     * @param areaId 信息唯一标识
     * @param parentId 父级id(为null或"")
     * @param areaName 名称
     * @return
     */
    boolean validRepeat(String areaId, String parentId, String areaName) {
        boolean isRepeat = false;
        areaId = StringUtil.trim(areaId);
        parentId = StringUtil.trim(parentId);
        areaName = StringUtil.trim(areaName);
        Area area = new Area();
        area.setParentId(parentId);
        area.setAreaName(areaName);
        area.setRecordFlag(Constants.INT_NORMAL);
        List<Area> areas = areaService.find(area);
        if(areas != null && !areas.isEmpty()) {
            if("".equals(areaId)) {
                isRepeat = true;
            }
            else {
                final String finalAreaId = areaId;
                isRepeat = areas.parallelStream().anyMatch(e -> !e.getAreaId().equals(finalAreaId));
            }
        }
        return isRepeat;
    }

    /**
     * 保存或更新
     * @param areaId 信息唯一标识
     * @param areaName 名称
     * @param areaDesc 描述
     * @param parentId 父级id(为null或"")
     * @return
     */
    public Map<String, Object> save(String areaId, String areaName, String areaDesc, String parentId) {
        try {
            areaId = StringUtil.trim(areaId);
            areaName = StringUtil.trim(areaName);
            parentId = StringUtil.trim(parentId);
            if ("".equals(areaName)) {
                ret = this.getParamErrorVo();
            }
            else {
                boolean isRepeat = validRepeat(areaId, parentId, areaName);
                if (isRepeat) {
                    ret = this.getParamRepeatErrorVo("地域名称");
                } else {
                    Area area = new Area();
                    area.setParentId(parentId);
                    area.setAreaName(areaName);
                    area.setAreaDesc(areaDesc);
                    area.setRecordFlag(Constants.INT_NORMAL);
                    if ("".equals(areaId)) {    // 新增
                        area.setAreaId(StringUtil.getRandomUUID());
                        areaService.save(area);
                    }
                    else {
                        area.setAreaId(areaId);
                        areaService.update(area);
                    }
                    ret = this.getSuccessVo("", "");
                }
            }
        }
        catch (Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 根据areaId获得地域信息
     * @param areaId 唯一标识
     * @return
     */
    public Map<String, Object> get(String areaId) {
        areaId = StringUtil.trim(areaId);
        if ("".equals(areaId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Area area = areaService.getById(areaId);
                if (area == null) {
                    ret = this.getParamErrorVo();
                }
                else {
                    ret = this.getSuccessVo("", area);
                }
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据areaId删除信息
     * @param areaId 唯一标识
     * @return
     */
    public Map<String, Object> deleteById(String areaId) {
        areaId = StringUtil.trim(areaId);
        if ("".equals(areaId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Area area = new Area();
                area.setAreaId(areaId);
                area.setRecordFlag(Constants.INT_DEL);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 检索
     * @param parentId 父级ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String, Object> search(String parentId,String key,int pageNum,int pageSize) {
        ret = null;
        try {
            Area area = new Area();
            area.setParentId(StringUtil.trim(parentId));
            area.setKey(StringUtil.trim(key));
            area.setRecordFlag(Constants.INT_NORMAL);
            PageInfo<Area> pageInfo = areaService.search(area, pageNum, pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
