package com.oa.platform.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全验证工具类
 * @author Feng
 * @date 2018/09/11
 */
public class SecurityUtil {

    private SecurityUtil() {

    }

    public static final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    /**
     * 使用BCrypt方式生成密码(不可逆)
     * @param src
     * @return 若src为null，则返回""
     */
    public static String encodeBCryptPassword(String src) {
        return src == null ? null : bCryptEncoder.encode(src);
    }

    /**
     * 使用BCrypt方式验证密码
     * @param rawPassword 明文密码
     * @param encodedPasword 加密后密码
     * @return
     */
    public static boolean matchesBCryptPassword(String rawPassword,String encodedPasword) {
        return bCryptEncoder.matches(rawPassword,encodedPasword);
    }
}
