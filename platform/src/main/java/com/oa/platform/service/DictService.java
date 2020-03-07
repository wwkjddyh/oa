package com.oa.platform.service;

import com.oa.platform.entity.Dict;

/**
 * 字典信息
 * @author Feng
 * @date 2018/08/23
 */
public interface DictService extends BaseService<Dict, String> {

    /**
     * 根据id修改标识
     * @param dictId
     * @param recordFlag
     */
    void updateDictFlagById(String dictId, Integer recordFlag);

}
