package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.Res;
import com.oa.platform.entity.ResDl;
import com.oa.platform.entity.UserRole;

import java.util.List;

/**
 * 资源信息
 * @author jianbo.feng
 * @date 2020/03/14
 */
public interface ResService extends BaseService<Res, String> {

    /**
     * 保存下载记录
     * @param resDl 下载记录
     */
    void saveResDl(ResDl resDl);

    /**
     * 批量保存下载记录
     * @param dlList 下载记录列表
     */
    void batchSaveResDl(List<ResDl> dlList);

    /**
     * 更新下载记录
     * @param resDl 下载记录
     */
    void updateResDl(ResDl resDl);

    /**
     * 删除下载记录
     * @param resDl 下载记录
     */
    void deleteResDl(ResDl resDl);

    /**
     * 查询下载记录
     * @param resDl 下载记录
     * @return
     */
    List<ResDl> findResDl(ResDl resDl);

    /**
     * 检索资源下载信息
     * @param resDl 下载记录
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<ResDl> searchResDl(ResDl resDl, int pageNum, int pageSize);
}
