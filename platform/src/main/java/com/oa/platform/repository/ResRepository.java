package com.oa.platform.repository;

import com.oa.platform.entity.Res;
import com.oa.platform.entity.ResDl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源
 * @author jianbo.feng
 * @date 2020/03/14
 */
@Repository
public interface ResRepository extends BaseRepository<Res, String> {

    /**
     * 插入下载记录
     * @param resDl 下载记录
     */
    void insertResDl(ResDl resDl);

    /**
     * 批量插入下载记录
     * @param dlList 下载记录列表
     */
    void batchInsertResDl(List<ResDl> dlList);

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

	List<String> getOrgIdByUserId(String userId);
}
