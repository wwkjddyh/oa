package com.oa.platform.util;

import com.oa.platform.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.oa.platform.common.Constants.*;

/**
 * 字符串工具类
 * @author Feng
 * @date 2018/08/18
 */
public class StringUtil {

    private StringUtil() {

    }

    static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    /**
     * (默认)随机(生成的)字符串长度为6
     */
    public static int RANDOM_STRING_LENGTH = 6;

    /**
     * (基础)字符串:"a-zA-Z0-9"
     */
    public static String BASE_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 编码随机字符串:"A-Z0-9"
     */
    public static String BASE_CODE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    /**
     * 获得随机唯一字符串
     * @return
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 返回结果集
     * @param code 状态编码
     * @param msg 异常消息
     * @param data 数据
     * @return {Map&lt;String,Object&gt;}
     */
    public static Map<String, Object> getResultVo(int code, String msg, Object data) {
        Map<String, Object> vo = new HashMap<>();
        vo.put(Constants.KEY_CODE,code);
        vo.put(Constants.KEY_MSG,msg);
        vo.put(Constants.KEY_DATA,data);
        return vo;
    }

    /**
     * 返回登录结果集
     * @param code 状态编码
     * @param msg 消息
     * @param data 数据
     * @param sessionId 会话id
     * @return
     */
    public static Map<String, Object> getLoginResultVo(int code, String msg, String sessionId , Object data) {
        Map<String, Object> vo = new HashMap<>();
        vo.put(Constants.KEY_CODE, code);
        vo.put(Constants.KEY_MSG, msg);
        vo.put(Constants.KEY_DATA, data);
        vo.put(Constants.KEY_SESSIONID, sessionId);
        return vo;
    }

    /**
     * 根据字符串Key从Map中获得值
     * @param data 若为null，则返回null
     * @param key 若为null或""，null
     * @return
     */
    public static Object getValByStrKey(Map<String, Object> data, String key) {
        return getValueByStrKey(data, key,null);
    }

    /**
     * 根据字符串Key从Map中获得值
     * @param data 若为null，则返回默认值
     * @param key 若为null或""，则返回默认值
     * @param def 若无法获取值，则返回默认值
     * @return
     */
    public static Object getValueByStrKey(Map<String, Object> data, String key, Object def) {
        key = trim(key);
        return getValByKey(data, key, def);
    }

    /**
     * 根据Key从Map中获得值
     * @param data 若为null，则返回默认值
     * @param key 若为null，则返回默认值
     * @param def 若无法获取值，则返回默认值
     * @return
     */
    public static Object getValByKey(Map<String, Object> data, Object key, Object def) {
        if(data == null || key == null) {
            return def;
        }
        else if(data.containsKey(key)) {
            Object value = data.get(key);
            return value == null ? def : value;
        }
        else {
            return def;
        }
    }


    /**
     * 字符串转数字
     * @param str 源字符串
     * @return integer
     */
    public static Integer strToInt(String str) {
        return strToInt(str,null);
    }

    /**
     * 字符串转数字
     * @param str 源字符串
     * @param defult 默认返回值
     * @return 若字符串转换Integer数字异常，则返回默认数字
     */
    public static Integer strToInt(String str, Integer defult) {
        if (!("".equals(str) || str == null)) {
            try {
                defult = Integer.valueOf(str.trim());
            }
            catch(NumberFormatException nfe) {
                nfe.printStackTrace();
                logger.debug(nfe.getMessage(), nfe);
            }
        }
        return defult;
    }

    /**
     * 字符串转数字
     * @param str 源字符串
     * @return integer
     */
    public static Long strToLong(String str) {
        return strToLong(str,null);
    }

    /**
     * 字符串转Long型数字
     * @param str 源字符串
     * @param defult 默认返回值
     * @return 若字符串转换Long型数字异常，则返回默认数字
     */
    public static Long strToLong(String str, Long defult) {
        if (!("".equals(str) || str == null)) {
            try {
                defult = Long.valueOf(str.trim());
            }
            catch(NumberFormatException nfe) {
                nfe.printStackTrace();
                logger.debug(nfe.getMessage(), nfe);
            }
        }
        return defult;
    }

    /**
     * object转换为Integer
     * @param object 源对象
     * @return
     */
    public static Integer objToInteger(Object object){
        Integer integer = null;
        if(null != object) {
            integer = strToInt(object.toString());
        }
        return integer;
    }

    /**
     * object转换为Integer
     * @param object 源对象
     * @return
     */
    public static Integer objToInteger(Object object, Integer defult){
        if(null != object) {
            defult = strToInt(object.toString());
        }
        return defult;
    }

    /**
     * object转换为Double
     * @param object 源对象
     * @return
     */
    public static Double objToDouble(Object object){
        Double dou = null;
        if(null != object) {
            dou = strToDouble(object.toString());
        }
        return dou;
    }

    /**
     * object转换为Double
     * @param object 源对象
     * @return
     */
    public static Double objToDouble(Object object, Double defult){
        if(null != object) {
            defult = strToDouble(object.toString());
        }
        return defult;
    }


    /**
     * String转换为Boolean
     * @param src
     * @return 仅当src为"true"时则返回true(对象Boolean)，{src不区分大小写}
     */
    public static Boolean strToBoolean(String src) {
        return Boolean.valueOf(src);
    }

    /**
     * String转换为boolean
     * @param src
     * @return 仅当src为"true"时则返回true(对象Boolean)，{src不区分大小写}
     */
    public static boolean strToBool(String src) {
        return Boolean.parseBoolean(src);
    }

    /**
     * String转为Double
     * @param src 字符串
     * @return 若字符串转换Double型数字异常，则返回null
     */
    public static Double strToDouble(String src) {
        return strToDouble(src,null);
    }

    /**
     * String转为Double
     * @param src 字符串
     * @param defult 默认值
     * @return 若字符串转换Double型数字异常，则返回defult
     */
    public static Double strToDouble(String src,Double defult) {
        if (!("".equals(src) || src == null)) {
            try {
                defult = Double.valueOf(src.trim());
            }
            catch(NumberFormatException nfe) {
                nfe.printStackTrace();
                logger.debug(nfe.getMessage(), nfe);
            }
        }
        return defult;
    }

    /**
     * String转为Float
     * @param src 字符串
     * @return 若字符串转换Float型数字异常，则返回null
     */
    public static Float strToFloat(String src) {
        return strToFloat(src,null);
    }

    /**
     * String转为Float
     * @param src 字符串
     * @param defult 默认值
     * @return 若字符串转换Float型数字异常，则返回defult
     */
    public static Float strToFloat(String src,Float defult) {
        if (!("".equals(src) || src == null)) {
            try {
                defult = Float.valueOf(src.trim());
            }
            catch(NumberFormatException nfe) {
                nfe.printStackTrace();
                logger.debug(nfe.getMessage(), nfe);
            }
        }
        return defult;
    }

    /**
     * String转为Short
     * @param src 字符串
     * @return 若字符串转换Short型数字异常，则返回null
     */
    public static Short strToShort(String src) {
        return strToShort(src,null);
    }

    /**
     * String转为Short
     * @param src 字符串
     * @param defult 默认值
     * @return 若字符串转换Short型数字异常，则返回defult
     */
    public static Short strToShort(String src,Short defult) {
        if (!("".equals(src) || src == null)) {
            try {
                defult = Short.valueOf(src.trim());
            }
            catch(NumberFormatException nfe) {
                nfe.printStackTrace();
                logger.debug(nfe.getMessage(), nfe);
            }
        }
        return defult;
    }

    /**
     * String转为Byte
     * @param src 字符串
     * @return 若字符串转换Byte型数字异常，则返回null
     */
    public static Byte strToByte(String src) {
        return strToByte(src,null);
    }

    /**
     * String转为Byte
     * @param src 字符串
     * @param defult 默认值
     * @return 若字符串转换Byte型数字异常，则返回defult
     */
    public static Byte strToByte(String src,Byte defult) {
        if (!("".equals(src) || src == null)) {
            try {
                defult = Byte.valueOf(src.trim());
            }
            catch(NumberFormatException nfe) {
                nfe.printStackTrace();
                logger.debug(nfe.getMessage(), nfe);
            }
        }
        return defult;
    }

    /**
     * 随机字符串
     * @param baseString 基础字符串(如果为null或空，则使用的默认的"a-zA-Z0-9")
     * @param length 表示生成字符串的长度(<b>length必须大于0,否则默认为1</b>)
     * @return
     */
    public static String randomString(String baseString,int length) {
        length = length <= 0 ? 1 : length;
        baseString = trim(baseString);
        int len = length;
        baseString = "".equals(baseString) ? BASE_STRING : baseString;
        if(baseString.length() < length) {
            len = baseString.length();
        }
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(baseString.length());
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机字符串
     * @param length 表示生成字符串的长度(<b>length必须大于0,否则默认为1</b>)
     * @return
     */
    public static String randomString(int length) {
        return randomString(null,length);
    }

    /**
     * 获得默认随机字符串，长度为6
     * @return
     */
    public static String getDefRandomString() {
        return randomString(null,RANDOM_STRING_LENGTH);
    }

    /**
     * 获得默认随机编码字符串，长度为6
     * @return
     */
    public static String getDefRandomCodeString() {
        return randomString(BASE_CODE_STRING,RANDOM_STRING_LENGTH);
    }

    /**
     * 获得子字符串
     * @param src 源字符串
     * @param splitLen 截取长度
     * @return
     */
    public static String subString(String src, int splitLen) {
        String dest = null;
        if(src != null && splitLen > 0) {
            int len = src.length();
            if(len > splitLen) {
                dest = src.substring(0, splitLen);
            }
            else {
                dest = src;
            }
        }
        return dest;
    }

    /**
     * 判断是否为数字(小数也可判断)
     * @param number 数字字符串
     * @return
     */
    public static boolean isNumber(String number) {
        if(number==null || "".equals(number))
            return false;
        int index = number.indexOf(".");
        if (index < 0) {
            return StringUtils.isNumeric(number);
        } else {
            String num1 = number.substring(0, index);
            String num2 = number.substring(index + 1);

            return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
        }
    }

    /**
     * 判断是否为数字(负数、小数也可判断)
     * @param number 数字字符串
     * @return
     */
    public static boolean isNumberBeNegative(String number) {
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        Boolean strResult = false;
        if(StringUtils.isNotEmpty(number)) {
            strResult = number.matches("-?[0-9]+.*[0-9]*");
        }
        return strResult;
    }

    /**
     * 去除空格(若为null或"null"则替换为"")
     * @param src 源字符串
     * @return
     * 如果为null，则返回""
     */
    public static String trim(String src) {
        return trim(src,"");
    }

    /**
     * 字符串去除(左右)空格,如果为空字符串或null则返回默认值
     * @param src 源字符串
     * @param def 默认字符串
     * @return
     */
    public static String trim(String src, String def) {
        src = src == null ? def : src.trim();
        return ("null".equals(src.toLowerCase(LOCALE_DEFAULT)) || "".equals(src)) ? def : src;
    }

    /**
     * Object转换为String,去除空格
     * @param object 源对象
     * @return
     */
    public static String trimObject(Object object) {
        return trim(String.valueOf(object));
    }

    /**
     * 如果对象为null，则替换为目标对象
     * @param object 源对象
     * @param def 默认对象
     * @return
     */
    public static Object trimObject(Object object, Object def) {
        return object == null ? def : object;
    }

    /**
     * 如果对象为"null"、null，则替换为目标字符串
     * @param object 源对象
     * @param def 默认字符串
     * @return
     */
    public static String trimObject(Object object, String def) {
        return trim(trimObject(object), def);
    }

    /**
     * 根据keys从map中获得值
     * @param map 图信息
     * @param keys 键组(如果第一个键获取不到值，则使用下一个键)
     * @return
     */
    public static Object getValByMapKey(Map<Object, Object> map, Object... keys) {
        Object ret = null;
        if(map != null && !map.isEmpty() && keys != null && keys.length > 0) {
            for(Object key : keys) {
                if(map.containsKey(key)) {
                    ret = map.get(key);
                    if(ret != null) {
                        break;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 根据keys从map中获得值(若不能获取值或获取值为null，则用默认值替换)
     * @param map 图信息
     * @param def 默认值
     * @param keys 键组(如果第一个键获取不到值，则使用下一个键)
     * @return
     */
    public static Object getValByMapKey(Map<Object, Object> map, Object def, Object... keys) {
        Object ret = getValByMapKey(map, keys);
        return ret == null ? def : ret;
    }

    /**
     * 根据keys从map中获得值
     * @param map 图信息
     * @param keys 键组(如果第一个键获取不到值，则使用下一个键)
     * @return
     */
    public static Object getValByMapKey(Map<String, Object> map, String... keys) {
        return getValByMapKey(map,keys);
    }

    /**
     * 根据keys从map中获得值(若不能获取值或获取值为null，则用默认值替换)
     * @param map 图信息
     * @param def 默认值
     * @param keys 键组(如果第一个键获取不到值，则使用下一个键)
     * @return
     */
    public static Object getValByMapKey(Map<String, Object> map, Object def, String... keys) {
        return getValByMapKey(map,def,keys);
    }

    /**
     * 根据key从map中获得值(若不能获取值或获取值为null，则用默认值替换)
     * @param map 图信息
     * @param key 键
     * @param def 默认值
     */
    public static Object getMapValueByKey(Map<Object, Object> map, Object key, Object def) {
        Object value = null;
        if (map != null) {
            value = map.getOrDefault(key, def);
        }
        return value == null ? def : value;
    }

    /**
     * 获得字符串长度(特别针对含有汉字的字符串)
     * @param value 字符串值
     * @param format 编码格式，如UTF-8、GBK; 默认为"UTF-8"
     * @return int
     */
    public static int getLength(String value, String format) {
        int valueLength = 0;
		value = trim(value);
        if(!"".equals(value)) {
            format = trim(format, DEFAULT_CHARSET).toUpperCase(LOCALE_DEFAULT);
            int byteLen = 1;    //单个汉字占位长度
            switch(format) {
                case CHARSET_GB2312 :
                case CHARSET_GBK :
                case CHARSET_GB18030 :
                case CHARSET_UTF16BE :
                case CHARSET_UTF16LE :
                    byteLen = 2; break;
                case CHARSET_ISO88591 : byteLen = 1; break;
                case DEFAULT_CHARSET : byteLen = 3; break;
                case CHARSET_UTF16 : byteLen = 4; break;
            }

//            String chinese = "[\u0391-\uFFE5]";
            for (int i = 0; i < value.length(); i++) {
                String temp = value.substring(i, i + 1);
                if (temp.matches(CHINESE_MATCHES)) {
                    valueLength += byteLen;
                } else {
                    valueLength += 1;
                }
            }
        }

        return valueLength;
    }

    /**
     * (文件路径)字符串过滤 {Fortify漏洞之 Path Manipulation 路径篡改问题解决}
     * @param aString
     * @return
     */
    public static String filterString(String aString) {
        aString = trim(aString);
        if (!"".equals(aString)) {
            String cleanString = "";
            for (int i = 0; i < aString.length(); ++i) {
                cleanString += filterChar(aString.charAt(i));
            }
            return cleanString;
        }
        return aString;
    }

    /**
     * (文件路径)字符过滤
     * @param aChar 字符串
     * @return
     */
    private static char filterChar(char aChar) {
        // 0 - 9
        for (int i = 48; i < 58; ++i) {
            if (aChar == i) return (char) i;
        }
        // 'A' - 'Z'
        for (int i = 65; i < 91; ++i) {
            if (aChar == i) return (char) i;
        }
        // 'a' - 'z'
        for (int i = 97; i < 123; ++i) {
            if (aChar == i) return (char) i;
        }
        // other valid characters
        switch (aChar) {
            case '/':
                return '/';
            case '.':
                return '.';
            case '-':
                return '-';
            case '_':
                return '_';
            case ' ':
                return ' ';
            case ':' :
                return ':';
            case '\\':
                return '\\';
        }

        return '%';
    }

    /**
     * Header-Manipulation 过滤
     * @param text 文本
     * @return
     */
    public static String headerManipulationFilter(String text) {
        text = trim(text);
        if (!"".equals(text)) {
            String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";
            Pattern pa = Pattern.compile(regex);
            Matcher ma = pa.matcher(text);
            if (ma.find()) {
                text = ma.replaceAll("");
            }
        }
        return text;
    }

    /**
     * 防SQL注入
     * @param str 字符串
     * @return
     */
    public static String transactSQLInjection(String str) {
        str = trim(str);
        return "".equals(str) ? "" : str.replaceAll(".*([';]+|(--)+).*", " ");
        // 另一种写法：return "".equals(str) ? "" : str.replaceAll("([';])+|(--)+","");
    }

    /**
     * 大写
     * @param src 源字符串
     * @return
     */
    public static String toUpperCase(String src) {
        return toUpperCase(src, "");
    }

    /**
     * 小写
     * @param src 源字符串
     * @return
     */
    public static String toLowerCase(String src) {
        return toLowerCase(src, "");
    }

    /**
     * 大写
     * @param src 源字符串
     * @param def 默认值
     * @return
     */
    public static String toUpperCase(String src, String def) {
        return trim(src, def).toUpperCase(LOCALE_DEFAULT);
    }

    /**
     * 小写
     * @param src 源字符串
     * @param def 默认值
     * @return
     */
    public static String toLowerCase(String src, String def) {
        return trim(src, def).toLowerCase(LOCALE_DEFAULT);
    }

    /**
     * 去除null或'null'字符串,默认替换为""
     * @param src 源字符串
     * @return
     */
    public static String trimNull(String src) {
        return trimNull(src, "");
    }

    /**
     * 去除null或'null'字符串,默认替换为默认字符串
     * @param src 源字符串
     * @param def 默认字符串
     * @return
     */
    public static String trimNull(String src, String def) {
        src = toLowerCase(src, def);
        if ("null".equals(src)) {
            src = def;
        }
        return src;
    }

}
