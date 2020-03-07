package com.oa.platform.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {

    /**
     * 名称
     * @return
     */
    String name() default "";

    /**
     * 类型
     * @return
     */
    String type() default "";

    /**
     * 描述
     * @return
     */
    String desc() default "";
}
