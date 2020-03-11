package com.oa.platform.service;

import com.oa.platform.entity.VerifyCode;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 验证码接口
 * @author jianbo.feng
 * @date 2020/03/11
 */
public interface VerifyCodeGen {

    /**
     * 生成验证码并返回code，将图片写的os中
     * @param width 宽
     * @param height 高
     * @param os 输出流
     * @return {String}
     * @throws IOException
     */
    String generate(int width, int height, OutputStream os) throws IOException;

    /**
     * 生成验证码对象
     * @param width 宽
     * @param height 高
     * @return {VerifyCode}
     * @throws IOException
     */
    VerifyCode generate(int width, int height) throws IOException;
}
