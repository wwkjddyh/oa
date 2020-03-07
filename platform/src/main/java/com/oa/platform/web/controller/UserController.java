package com.oa.platform.web.controller;

import com.oa.platform.biz.UserBiz;
import com.oa.platform.entity.User;
import com.oa.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 * @author Feng
 * @date 2019/03/01
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBiz userBiz;

    @ResponseBody
    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }

    @ResponseBody
    @GetMapping("/get/{id}")
    public User get(@PathVariable String id) {
        return userService.getById(id);
    }

    @ResponseBody
    @GetMapping("save")
    public String save() {
        userBiz.saveUser(User.TYPE_PERSON,"test003","测试账号003","123456", "");
        return "success";
    }
}
