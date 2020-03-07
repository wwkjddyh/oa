package com.oa.platform.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 集合工具类
 * @author Feng
 * @date 2019/03/01
 */
public class CollectionUtil {

    private CollectionUtil() {

    }

    /**
     * Iterable转换为List
     * @param iterable
     * @return
     */
    public static List iterableToList(Iterable iterable) {
        List list = null;
        if(iterable != null) {
            list = new ArrayList();
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        }
        return list;
    }
}
