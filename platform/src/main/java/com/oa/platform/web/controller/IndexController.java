package com.oa.platform.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * @author Feng
 * @date 2019/03/01
 */
@Controller
public class IndexController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
