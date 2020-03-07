
/**
 * 前端工具类
 */

//设置cookie键值对,值解析为JSON字符串
var setCookie = function(key, value) {
	var str = JSON.stringify(value)	
	document.cookie = key + "=" + str;
}

//在cookie中通过键名获取JSON格式的值并解析为对象,若未取到则返回空
var getCookie = function(cname) {
    var name = cname + "=";
    var cookieValue = document.cookie.trim();
    return cookieValue.indexOf(name)==0?JSON.parse(cookieValue.substring(name.length,cookieValue.length)):null;
}

//时间日期格式化
var format = function(time, format) {
	var t = new Date(time);
	var tf = function(i){return (i < 10 ? '0' :'') + i};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a)
	{
		switch(a)
		{
			case 'yyyy':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'dd':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
		}
	})
}

/**
 * 中文字符编码长度
 */
var CNCharFormatLen = {
    "GB2312": 2,
    "GBK" : 2,
    "GB18030" : 2,
    "ISO-8859-1" : 1,
    "UTF-8" : 3,
    "UTF-16" : 4,
    "UTF-16BE" : 2,
    "UTF-16LE" : 2
};

/**
 * 去除字符串空格以及将str为null或undefined格式化为空格
 * @param str
 * @returns
 */
var trimNull = function(str) {
    return (str == undefined || str == null || str == 'undefined' || str == 'null') ? "" : str;
}

/**
 * 获得(包含汉字)字符串长度
 * @param str 若为null或undefined,则长度为0
 * @param format 编码格式(如：GBK、UTF-8; GBK为占位2个字节，UTF-8为占位3个字节),默认为UTF-8
 */
var getStrLength = function(str, format) {
    str = (str == undefined || str == null) ? "" : str;
    var realLength = 0;
    if("" != str) {
        format = trimNull(format);
        format = "" == format ? "UTF-8" : format.toUpperCase();
        var len = str.length;
        var charCode = -1;
        for(var i = 0; i < len; i++){
            charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) {
                realLength += 1;
            }else{
                realLength += CNCharFormatLen[format];
            }
        }
    }
    return realLength;
}
