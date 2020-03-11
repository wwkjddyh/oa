package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 验证码
 * @author jianbo.feng
 * @date 2020/03/11
 */
public class VerifyCode implements Serializable {

    /**
     * (字符串)码
     */
    private String code;

    /**
     * 图像字节组
     */
    private byte[] imgBytes;

    /**
     * 过期时间
     */
    private long expireTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public VerifyCode() {
    }

    public VerifyCode(String code, byte[] imgBytes, long expireTime) {
        this.code = code;
        this.imgBytes = imgBytes;
        this.expireTime = expireTime;
    }
}
