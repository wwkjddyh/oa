package com.oa.platform.repository;

import java.util.List;

/**
 * 基础数据处理接口
 * @author Feng
 * @date 2019/03/01
 * @param <T>
 */
public interface BaseRepository<T, ID> {

    /**
     * 插入数据
     * @param t
     */
    void insert(T t);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T findById(ID id);

    /**
     * 删除数据
     * @param t
     */
    void delete(T t);

    /**
     * 更新数据
     * @param t
     */
    void update(T t);


    /**
     * 按(实体构成)条件查询
     * @param t 由实体构成的查询条件
     * @return
     */
    List<T> find(T t);
}
