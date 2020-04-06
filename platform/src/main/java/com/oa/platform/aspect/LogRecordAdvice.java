package com.oa.platform.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志通知
 */
@Component
@Slf4j
@Aspect
public class LogRecordAdvice {

    @Autowired
    private HttpServletRequest request;

    private final Logger logger = LoggerFactory.getLogger(LogRecordAdvice.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     *一 、利用注解的方式
     */
    @Before("@annotation(logRecord)")
    public void before(JoinPoint point, LogRecord logRecord){
//    	logger.info("前置增强");
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容

//        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ipAddress = request.getRemoteAddr();

        // 记录下请求内容
//        logger.info("URL : " + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + request.getRemoteAddr());
//        logger.info("CLASS_METHOD : " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(point.getArgs()));
//        logger.info("ip:" +ipAddress);

    }

    @AfterReturning(returning = "obj", pointcut="@annotation(logRecord)")
    public void after(JoinPoint point, Object obj, LogRecord logRecord){
        // obj 为接口的返回值（用于记录操作对象）, logRecord 为接口上打得注解
//    	logger.info("后置增强");
        String name = logRecord.name();
        String type = logRecord.type();
        String desc = logRecord.desc();
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("name", name);
        data.put("desc", desc);
//        logger.info("LogRecordAdvice.after()=> obj ：" + JSON.toJSONString(obj));
//        logger.info("data to json => " + JSON.toJSONString(data));

        // 处理完请求，返回内容
//        logger.info("RESPONSE : " + obj);
//        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));

    }

    @AfterThrowing("@annotation(logRecord)")
    public void afterThrowing(JoinPoint point, LogRecord logRecord){

    }


    /**
     *二、Pointcut的方式，完成对com.insectcontrol.controller包下所有类增强
     */
    @Pointcut("within(com.oa.platform.web.controller..*)")
    public void adminValidatePointcut(){
    }

    @Before("adminValidatePointcut()")
    public void adminValidateBefore(){
    	//logger.info("Pointcut的方式，前置增强");
    }

}

// : @Around环绕通知
// : @Before通知执行
// : @Before通知执行结束
// : @Around环绕通知执行结束
// : @After后置通知执行了!
// : @AfterReturning第一个后置返回通知的返回值：18
