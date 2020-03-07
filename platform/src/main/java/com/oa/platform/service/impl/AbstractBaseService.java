package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务处理抽象类
 * @author Feng
 * @date 2018/08/23
 */
public abstract class AbstractBaseService<T,ID> {

    protected Class<T> domainClass;

    @Autowired
    protected BaseRepository<T,ID> repository;


    public AbstractBaseService(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    /**
     * 保存
     * @param t
     */
    @Transactional
    public void save(T t) {
        if(t != null) {
            repository.insert(t);
        }
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public T getById(ID id) {
        return repository.findById(id);
    }

    /**
     * 删除数据
     * @param t
     */
    @Transactional
    public void delete(T t) {
        repository.delete(t);
    }

    /**
     * 更新数据
     * @param t
     */
    @Transactional
    public void update(T t) {
        repository.update(t);
    }

    /**
     * 根据条件查询
     * @param t
     * @return
     */
    public List<T> find(T t) {
        return repository.find(t);
    }

    /**
     * 根据条件分页查询
     * @param t 由实体构成的查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public PageInfo<T> search(T t, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        System.err.println("class.name => " + domainClass.getClass().getName());
        return new PageInfo<>(repository.find(t));
    }

}
