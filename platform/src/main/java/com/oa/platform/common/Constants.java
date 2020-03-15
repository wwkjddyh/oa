package com.oa.platform.common;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局常量
 * @author Feng
 * @date 2018/08/23
 */
public class Constants {

    /**
     * SPRING_SECURITY框架的Session Key
     * <br>"SPRING_SECURITY_CONTEXT"</br>
     */
    public final static String SPRING_SECURITY_SESSION_KEY = "SPRING_SECURITY_CONTEXT";

    /**
     * 信息返回数据中key：code
     */
    public final static String KEY_CODE = "code";

    /**
     * 信息返回数据中key：msg
     */
    public final static String KEY_MSG = "msg";

    /**
     * 信息返回数据中key：data
     */
    public final static String KEY_DATA = "data";

    /**
     * 信息返回数据中key：sessionId
     */
    public final static String KEY_SESSIONID = "sessionId";

    /**
     * 字符串标识-正常：'NORMAL'
     */
    public final static String STR_NORMAL = "NORMAL";

    /**
     * 字符串标识-删除：'DELETE'
     */
    public final static String STR_DELETE = "DELETE";

    /**
     * 字符串标识-冻结：'FREEZE'
     */
    public final static String STR_FREEZE = "FREEZE";

    /**
     * 字符串标识-成功：'SUCCESS'
     */
    public final static String STR_SUCCESS = "SUCCESS";

    /**
     * 字符串标识-失败：'FAILURE'
     */
    public final static String STR_FAILURE = "FAILURE";

    /**
     * 数字标识-正常：1
     */
    public final static int INT_NORMAL = 1;

    /**
     * 数字标识-删除：0
     */
    public final static int INT_DEL = 0;

    /**
     * 数字标识-冻结：-1
     */
    public final static int INT_FREEZE = -1;

    /**
     * 字符串：true （'T'）
     */
    public final static String STR_TRUE = "T";

    /**
     * 字符串：false （'F'）
     */
    public final static String STR_FALSE = "F";

    /**
     * 布尔值: TRUE (true)
     */
    public final static boolean TRUE = true;

    /**
     * 布尔值: FALSE (true)
     */
    public final static boolean FALSE = false;

    /**
     * 成功: SUCCESS (true)
     */
    public final static boolean SUCCESS = true;

    /**
     * 成功: FAILURE (false)
     */
    public final static boolean FAILURE = false;

    /** 图片格式 */
    public final static Map<String,String> PIC_FORMATS = new HashMap<String,String>(){{
        put("bmp",".bmp");put("jpg",".jpg");put("png",".png");put("tiff",".tiff");
        put("gif",".gif");put("pcx",".pcx");put("tga",".tga");put("exif",".exif");
        put("cdr",".cdr");put("dxf",".dxf");put("ufo",".ufo");put("eps",".eps");
        put("ai",".ai");put("raw",".raw");put("wmf",".wmf");put("webp",".webp");
        put("svg",".svg");put("psd",".psd");put("fpx",".fpx");put("jpeg",".jpeg");
    }};

    /**
     * 分页信息：总数
     */
    public final static String PAGE_INFO_TOTAL = "total";

    /**
     * 分页信息：列表
     */
    public final static String PAGE_INFO_LIST = "list";

    /**
     * 分页信息：页码
     */
    public final static String PAGE_INFO_PAGE_NUM = "pageNum";

    /**
     * 分页信息：每页记录数
     */
    public final static String PAGE_INFO_PAGE_SIZE = "pageSize";

    /**
     * 分页信息：总页数
     */
    public final static String PAGE_INFO_PAGES = "pages";

    /**
     * EXCEL文件后缀格式：xls
     */
    public final static String EXCEL_SUFFIX_XLS = "xls";

    /**
     * EXCEL文件后缀格式：xlsx
     */
    public final static String EXCEL_SUFFIX_XLSX = "xlsx";

    /**
     * 二维码图片宽度：400（默认）
     */
    public final static int QRCODE_WIDTH = 400;

    /**
     * 二维码图片高度：400（默认）
     */
    public final static int QRCODE_HEIGHT = 400;

    /**
     * 默认密码：123456
     */
    public final static String DEF_PASSWORD = "123456";

    public static final String WEBSOCKET_USERNAME = "websocket_username";
    public static final String SESSION_USERNAME = "session_username";

    /**
     * 使用SESSION_KEY区分WebSocketHandler，以便定向发送消息
     */
    public static final String WEBSOCKET_SESSION_KEY = "websocket_key";

    /**
     * 用于保存用户会话信息
     * {key:登录名或登录名加sessionid；value：用户登录会话}
     */
    public static final ConcurrentHashMap<String, WebSocketSession> USER_SESSIONS = new ConcurrentHashMap<>();

    /**
     * 默认字符串编码：UTF-8
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 字符串编码格式：GB2312
     */
    public static final String CHARSET_GB2312 = "GB2312";

    /**
     * 字符串编码格式：GBK
     */
    public static final String CHARSET_GBK = "GBK";

    /**
     * 字符串编码格式：GB18030
     */
    public static final String CHARSET_GB18030 = "GB18030";

    /**
     * 字符串编码格式：UTF-16BE
     */
    public static final String CHARSET_UTF16BE = "UTF-16BE";

    /**
     * 字符串编码格式：UTF-16LE
     */
    public static final String CHARSET_UTF16LE = "UTF-16LE";

    /**
     * 字符串编码格式：ISO-8859-1
     */
    public static final String CHARSET_ISO88591 = "ISO-8859-1";

    /**
     * 字符串编码格式：UTF-16
     */
    public static final String CHARSET_UTF16 = "UTF-16";

    /**
     * 本地化：默认（CHINESE）
     */
    public static final Locale LOCALE_DEFAULT = Locale.CHINESE;

    /**
     * 中文匹配:
     */
    public static final String CHINESE_MATCHES = "[\u0391-\uFFE5]";

    /**
     * 校验码(VerifyCode) Key: PlatformVerifyCode
     */
    public static final String VERIFY_CODE = "PlatformVerifyCode";

    /**
     * 接收者类型：用户(0)
     */
    public static final int RECEIVER_TYPE_USER = 0;

    /**
     * 接收者类型：角色(1)
     */
    public static final int RECEIVER_TYPE_ROLE = 1;

    /**
     * 是否为菜单：是(1)
     */
    public static final int IS_MENU = 1;

    /**
     * 是否为菜单：否(0)
     */
    public static final int IS_NOT_MENU = 0;

    /**
     * 是否已读：是(1)
     */
    public static final int VIEWED = 1;

    /**
     * 是否已读：否(0)
     */
    public static final int UN_VIEWED = 0;
}
