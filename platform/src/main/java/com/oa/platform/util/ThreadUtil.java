package com.oa.platform.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 使用Thead获取信息
 * @author jianbo.feng
 * @date 2019/07/02
 */
public class ThreadUtil {

    private ThreadUtil() {

    }

    /**
     * 获得(调用者)当前函数的函数名
     * @return
     */
    public static String getCurrentMethodName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return elements[2].getMethodName();
    }

    /**
     * 获得(调用者)当前函数的类名
     * @return
     */
    public static String getCurrentMethodClassName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return elements[2].getClassName();
    }

    /**
     * 获得(调用者)当前函数的函数名(包含完整类名)
     * @return
     */
    public static String getCurrentFullMethodName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement element = elements[2];
        return element.getClassName() + "." + element.getMethodName();
    }

    /**
     * 获得(调用者)当前函数的元信息(函数名、行数、类名、文件名、是否为本地函数)
     * @return
     */
    public static Map<String, Object> getCurrentMethodMeta() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement element = elements[2];
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("MethodName", element.getMethodName());
        ret.put("ClassName", element.getClassName());
        ret.put("FileName", element.getFileName());
        ret.put("LineNumber", element.getLineNumber());
        ret.put("isNativeMethod", element.isNativeMethod());
        return ret;
    }
}
