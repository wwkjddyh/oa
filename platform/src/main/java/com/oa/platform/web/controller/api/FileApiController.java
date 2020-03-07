package com.oa.platform.web.controller.api;

import com.oa.platform.biz.FileBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文件上传下载处理
 * @author Feng
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/api/file")
public class FileApiController extends BaseController {

    @Autowired
    private FileBiz fileBiz;

    /**
     * 单文件上传
     * @param file 文件
     * @param request HttpServlet请求对象
     */
    @RequestMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return fileBiz.upload(file, request);
    }

    /**
     * 批量上传(多文件上传)
     * @param request HttpServlet请求对象
     */
    @RequestMapping("/batch/upload")
    public Map<String, Object> batchUpload(HttpServletRequest request) {
        return fileBiz.batchUpload(request);
    }


    /**
     * 文件下载
     * @param response HttpServlet响应对象
     */
    @RequestMapping("/dl/{fname}")
    public void dl(HttpServletResponse response, @PathVariable String fname) {
        fileBiz.dl(response, fname);
    }

}
