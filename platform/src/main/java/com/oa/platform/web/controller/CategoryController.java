package com.oa.platform.web.controller;

import com.oa.platform.biz.CategoryBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类别
 * @author Feng
 * @date 2019/03/15
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryBiz categoryBiz;
}
