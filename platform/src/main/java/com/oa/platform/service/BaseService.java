package com.oa.platform.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 基础业务接口
 * @author Feng
 * @date 2018/08/23
 */
public interface BaseService<T,ID> {

    /**
     * 保存
     * @param t
     */
    void save(T t);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T getById(ID id);

    /**
     * 根据条件删除
     * @param t
     */
    void delete(T t);

    /**
     * 根据条件删除
     * @param t
     */
    void update(T t);

    /**
     * 根据条件查询
     * @param t
     * @return
     */
    List<T> find(T t);

    /**
     * 分页查询
     * @param t 由实体构成的查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<T> search(T t, int pageNum, int pageSize);

}
