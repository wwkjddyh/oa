package com.oa.platform.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 时间工具类
 * @author Feng
 * @date 2018/08/23
 */
public class DateUtil {

    private DateUtil() {

    }

    static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 时间格式化 默认(年-月-日 时:分:秒) yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式化 年-月-日 yyyy-MM-dd
     */
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    /**
     * 时间格式化 月-日 MM-dd
     */
    public static final String FORMAT_MONTH_DAY = "MM-dd";

    /**
     * 时间格式化 年-月-日 时 yyyy-MM-dd HH
     */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR = "yyyy-MM-dd HH";

    /**
     * 时间格式化 年-月-日 时:分 yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 时间格式化 时:分:秒  HH:mm::ss
     */
    public static final String FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * MySQL时间格式化 默认(年-月-日 时:分:秒) %Y-%m-%d %H:%i:%s
     */
    public static final String MYSQL_DEFAULT_FORMAT = "%Y-%m-%d %H:%i:%s";

    /**
     * MySQL时间格式化 年 %Y
     */
    public static final String MYSQL_FORMAT_YEAR = "%Y";

    /**
     * MySQL时间格式化 年-月 %Y-%m
     */
    public static final String MYSQL_FORMAT_YEAR_MONTH = "%Y-%m";

    /**
     * MySQL时间格式化 年-月-日 %Y-%m-%d
     */
    public static final String MYSQL_FORMAT_YEAR_MONTH_DAY = "%Y-%m-%d";

    /**
     * MySQL时间格式化 月-日 %m-%d
     */
    public static final String MYSQL_FORMAT_MONTH_DAY = "%m-%d";

    /**
     * MySQL时间格式化 年-月-日 时 %Y-%m-%d %H
     */
    public static final String MYSQL_FORMAT_YEAR_MONTH_DAY_HOUR = "%Y-%m-%d %H";

    /**
     * MySQL时间格式化 年-月-日 时:分 %Y-%m-%d %H:%i
     */
    public static final String MYSQL_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "%Y-%m-%d %H:%i";

    /**yyyy-MM-dd HH:mm:ss*/
    public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(DEFAULT_FORMAT);

    /**yyyy-MM-dd*/
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);

    /**HH:mm:ss*/
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat(FORMAT_HOUR_MINUTE_SECOND);

    /**yyyy-MM*/
    public static final SimpleDateFormat FORMAT_YEAR_MONTH = new SimpleDateFormat("yyyy-MM");

    /**yyyyMMddHHmmss*/
    public static final SimpleDateFormat FORMAT_NUMBER = new SimpleDateFormat("yyyyMMddHHmmss");

    /**yyyyMMdd*/
    public static final SimpleDateFormat FORMAT_DATE1 = new SimpleDateFormat("yyyyMMdd");

    /**yyyyMM*/
    public static final SimpleDateFormat FORMAT_DATE2 = new SimpleDateFormat("yyyyMM");

    /**dd*/
    public static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat("dd");

    /**yyyy*/
    public static final SimpleDateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");

    /**MM*/
    public static final SimpleDateFormat FORMAT_MONTH = new SimpleDateFormat("MM");

    /**
     * 时间转字符串
     * @param date
     * @param dateFmt
     * @return
     */
    public static String date2Str(Date date, SimpleDateFormat dateFmt) {
        if (date == null)
            return "";
        return new SimpleDateFormat(dateFmt.toPattern()).format(date);
    }

    /**
     * 字符串转时间
     * @param dateStr
     * @param dateFmt
     * @return
     */
    public static Date str2Date(String dateStr, SimpleDateFormat dateFmt) {
        if (dateStr != null && dateFmt != null) {
            try {
                return new SimpleDateFormat(dateFmt.toPattern()).parse(dateStr);
            } catch (Exception e) {

            }
        }
        return null;
    }

    /**
     * @return
     */
    public static String getHandleDate() {
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_MONTH, -1);
        String dt = date2Str(cl.getTime(), FORMAT_DATE_TIME);

        return dt;

    }

    /** 日期转字符串，采用“yyyy-MM-dd”的表示形式 */
    public static String getShortDate(Date nDate) {
        if (nDate == null)
            return "";
        return new SimpleDateFormat(FORMAT_DATE.toPattern()).format(nDate);
    }

    /**
     * 获取月份的第一天
     * @param day
     * @return
     */
    public static Date computeFirstDayOfMonth(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        // 月份的第一天.
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取月份的最后一天
     * @param day
     * @return
     */
    public static Date computeLastDayOfMonth(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        // 月份的最后一天.
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int td = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, td - 1);
        //		calendar.set(calendar.MONTH, +1);
        //		calendar.set(Calendar.DAY_OF_MONTH, -);
        return calendar.getTime();
    }

    /**
     * 获取一个日期的后面N天的日期
     * @param d
     * @param n
     * @return
     */
    public static Date getDay(Date d, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int td = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, td + n);
        Date towDay = c.getTime();
        return towDay;
    }

    /**
     * 获取日期前N天的Date日期
     * @param d
     * @param n
     * @return
     */
    public static Date getPreNDay(Date d, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int td = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, td - n);
        Date towDay = c.getTime();
        return towDay;
    }

    /**
     * 获取前多少天的时间,格式由FMT决定
     * @param day
     * @return
     */
    public static String getPastDateStr(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -day);
        return FORMAT_DATE1.format(cal.getTime());
    }

    /**
     * 获取指定月份的，前（或后）几个月的月份
     * @param month
     * @param beforeOrAfterNum
     * @return
     */
    public static String getBeforeOrAfterMonthStr(String month, int beforeOrAfterNum) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY.replaceAll("-",""));
            Date date = sdf.parse(month + "01");
            Date nextDate = getBeforeOrAfterMonth(date, beforeOrAfterNum);
            return sdf.format(nextDate).substring(0, 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取前多少月的时间,格式由FMT决定
     * @param month
     * @return
     */
    public static String getPastMonthStr(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -month);
        return FORMAT_DATE2.format(cal.getTime());
    }

    /**
     * 获取前多少月的时间,格式由FMT决定
     * @param pointMonth
     * @param month
     * @return
     */
    public static String getPastMonthStr(String pointMonth, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(pointMonth.substring(0, 4)), Integer.parseInt(pointMonth.substring(4, 6)) - 1, 1);
        cal.add(Calendar.MONTH, -month);
        return FORMAT_DATE2.format(cal.getTime());
    }

    public static String getDateStr(Date date) {
        return FORMAT_DATE1.format(date);
    }

    /**
     * 获得之前或之后多少小时的日期
     * @author jianbo.feng
     * @param date
     * @param hour
     * @return
     */
    public static Date getBeforeOrAfterHour(Date date, int hour) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 获得之前或之后多少天的日期
     * @author jianbo.feng
     * @param date
     * @param day
     * @return
     */
    public static Date getBeforeOrAfterDay(Date date, int day) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 获得之前或之后多少月的日期
     * @author jianbo.feng
     * @param date
     * @param month
     * @return
     */
    public static Date getBeforeOrAfterMonth(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获得之前或之后多少年的日期
     * @author jianbo.feng
     * @param date
     * @param year
     * @return
     */
    public static Date getBeforeOrAfterYear(Date date, int year) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /***
     * 根据起止日期获得(包括起止日期在内的)所有日期的列表
     * @param startDate {yyyyMMdd}
     * @param endDate {yyyyMMdd}
     * @return
     */
    public static List<String> getDateRangesDate(String startDate,String endDate) {
        List<String> list = new ArrayList<>(0);
        List<String[]> timeStrs = getDateRanges(startDate,endDate);
        if(!timeStrs.isEmpty()) {
            for(String[] sStrs : timeStrs) {
                for(int i=Integer.parseInt(sStrs[0]);i<=Integer.parseInt(sStrs[1]);i++) {
                    String _s = i+"";
                    if(!list.contains(_s)) {
                        list.add(_s);
                    }
                }
            }
        }
        return list;
    }

    /***
     * 根据起止日期获得每月起止日期组信息
     * @param startDate {yyyyMMdd}
     * @param endDate {yyyyMMdd}
     * @return
     */
    public static List<String[]> getDateRanges(String startDate,String endDate) {
        String $d1 = startDate.substring(6, 8),$d2 = endDate.substring(6, 8);
        int y1 = Integer.parseInt(startDate.substring(0, 4)),y2 = Integer.parseInt(endDate.substring(0, 4));
        int m1 = Integer.parseInt(startDate.substring(4, 6)),m2 = Integer.parseInt(endDate.substring(4, 6));
        int yearSpace = 0;
        List<String[]> timeStrs = new ArrayList<>(0);
        if(y1 < y2) {
            yearSpace = y2 - y1;
        }
        List<Integer> monthList = Arrays.asList(new Integer[]{1,3,5,7,8,10,12});
        if(yearSpace == 0) {
            for(int i=m1;i<=m2;i++) {
                String iStr = (i <= 9)?"0"+i:i+"";
                String sStr = "",eStr = "";
                if(i == m1) { sStr = y1+""+iStr+""+$d1; }
                else { sStr = y1+""+iStr+"01"; }

                if(i== m2) { eStr = y1+""+iStr+""+$d2; }
                else { String monthEndDay = "30"; if(monthList.contains(i)) { monthEndDay = "31"; } eStr = y1+""+iStr+monthEndDay; }
                timeStrs.add(new String[]{sStr,eStr});
            }
        }
        else {
            for(int i=m1;i<=12;i++) {
                String iStr = (i <= 9)?"0"+i:i+"";
                String sStr = "",eStr = "";
                if(i == m1) { sStr = y1+""+iStr+""+$d1; } else { sStr = y1+""+iStr+"01"; }
                String monthEndDay = "30";
                if(monthList.contains(i)) { monthEndDay = "31"; }
                eStr = y1+""+iStr+monthEndDay;
                timeStrs.add(new String[]{sStr,eStr});
            }

            for(int i=1;i<=m2;i++) {
                String iStr = (i <= 9)?"0"+i:i+"";
                String sStr = y2+""+iStr+"01",eStr = "";
                if(i == m2) { eStr = y2+""+iStr+""+$d2; }
                else { String monthEndDay = "30"; if(monthList.contains(i)) { monthEndDay = "31"; } eStr = y2+""+iStr+monthEndDay; }
                timeStrs.add(new String[]{sStr,eStr});
            }
        }
        return timeStrs;
    }

    /**
     * java.util.Date 转换成 java.sql.Timestamp
     *
     * @param utilDate
     * @return
     */
    public static Timestamp utilDateToTimestamp(Date utilDate) {
        return (utilDate == null) ? new Timestamp(new Date().getTime()) : new Timestamp(utilDate.getTime());
    }

    /**
     * 字符串格式化为时间
     *
     * @param src
     * @param format
     *            格式化，如"yyyy-MM-dd HH:mm:ss";若为空，则使用默认格式化
     * @return
     */
    public static Date strToDate(String src, String format) {
        Date dest = null;
        src = StringUtil.trim(src);
        if (!"".equals(src)) {
            format = StringUtil.trim(format,DEFAULT_FORMAT);
            try {
                dest = new SimpleDateFormat(format).parse(src);
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
            }
        }
        return dest;
    }

    /**
     * 时间转为字符串
     *
     * @param src
     *            {java.util.Date}
     * @param format
     *            格式化，如"yyyy-MM-dd HH:mm:ss";若为空，则使用默认格式化
     * @return 空串
     */
    public static String dateToStr(Date src, String format) {
        String dest = "";
        if (src != null) {
            format = StringUtil.trim(format, DEFAULT_FORMAT);
            try {
                dest = new SimpleDateFormat(format).format(src);
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
            }
        }
        return dest;
    }

    /**
     * 当前时间格式化
     * @param format 格式化，如"yyyy-MM-dd HH:mm:ss";若为空，则使用:默认(年-月-日 时:分:秒) yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String currDateFormat(String format) {
        return dateToStr(new Date(), format);
    }

    /**
     * 添加年
     *
     * @param src
     *            {java.util.Date}
     * @param year
     * @return
     */
    public static Date addYear(Date src, int year) {
        Date dest = null;
        if (src != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(src);
            ca.add(Calendar.YEAR, year);
            dest = ca.getTime();
        }
        return dest;
    }

    /**
     * 添加月
     *
     * @param src
     *            {java.util.Date}
     * @param month
     * @return
     */
    public static Date addMonth(Date src, int month) {
        Date dest = null;
        if (src != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(src);
            ca.add(Calendar.MONTH, month);
            dest = ca.getTime();
        }
        return dest;
    }

    /**
     * 添加日
     *
     * @param src
     *            {java.util.Date}
     * @param day
     * @return
     */
    public static Date addDay(Date src, int day) {
        Date dest = null;
        if (src != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(src);
            ca.add(Calendar.DATE, day);
            dest = ca.getTime();
        }
        return dest;
    }

    /**
     * 添加时
     * @param src {java.util.Date}
     * @return
     */
    public static Date addHour(Date src, int hour) {
        Date dest = null;
        if (src != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(src);
            ca.add(Calendar.HOUR_OF_DAY, hour);
            dest = ca.getTime();
        }
        return dest;
    }

    /**
     * 添加分
     *
     * @param src
     *            {java.util.Date}
     * @param minute
     * @return
     */
    public static Date addMinute(Date src, int minute) {
        Date dest = null;
        if (src != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(src);
            ca.add(Calendar.MINUTE, minute);
            dest = ca.getTime();
        }
        return dest;
    }

    /**
     * 添加秒
     *
     * @param src
     *            {java.util.Date}
     * @param second
     * @return
     */
    public static Date addSecond(Date src, int second) {
        Date dest = null;
        if (src != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(src);
            ca.add(Calendar.SECOND, second);
            dest = ca.getTime();
        }
        return dest;
    }

    /**
     * 添加时间
     *
     * @param src
     *            {java.util.Date}
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date addTime(Date src, int year, int month, int day, int hour, int minute, int second) {
        Date dest = null;
        if (src != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(src);
            ca.add(Calendar.YEAR, year);
            ca.add(Calendar.MONTH, month);
            ca.add(Calendar.DATE, day);
            ca.add(Calendar.HOUR_OF_DAY, hour);
            ca.add(Calendar.MINUTE, minute);
            ca.add(Calendar.SECOND, second);
            dest = ca.getTime();
        }
        return dest;
    }

    /**
     * 根据sys.properties中的配置信息返回获取数据的时间限制
     *
     * @return
     *//*

	public static String limitCallTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		// 查看是否开启了获取规则
		String getDataOnOff = PropertiesUtil.getPropertyValue(PropertiesUtil.SYS_CONFIG_PATH, "getDataOnOff", "0");
		String limitDate = "0";
		if ("1".equals(getDataOnOff)) {
			limitDate = PropertiesUtil.getPropertyValue(PropertiesUtil.SYS_CONFIG_PATH, "gatDateLimit", "0");
		}
		// 具体的规则时间 默认为当前时间 now()

		Calendar c = Calendar.getInstance();
		int day = -Integer.valueOf(limitDate);
		c.add(Calendar.DATE, day);
		Date monday = c.getTime();
		String limitCallTime = sdf.format(monday);
		System.out.println(limitCallTime);
		return limitCallTime;
	}*/

    /**
     * 验证时间是否为时间格式
     * @param str
     * @return
     */
    public static boolean isValidDate(String str, String format) {
        boolean flag = true;
        try {
            str = StringUtil.trim(str);
            if(!"".equals(str)) {
                format = StringUtil.trim(format,DEFAULT_FORMAT);
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false);
                sdf.parse(str);
            }
        } catch (ParseException e) {
            //e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            flag = false;
        }
        return flag;
    }

    /**
     * 两个时间相差多少天
     * @param startTime 时间参数 1 格式：1990-01-01
     * @param endTime 时间参数 2 格式：2009-01-01
     * @return int 返回值为：例:1(天)
     */
    public static int getDistanceDay(String startTime, String endTime, String format) {
        int res = 0;
        startTime = StringUtil.trim(startTime);
        endTime = StringUtil.trim(endTime);
        format = StringUtil.trim(format, FORMAT_YEAR_MONTH_DAY);
        if(!"".equals(startTime) && !"".equals(endTime)) {
            long day = 0;
            try {
                Date one = strToDate(startTime, format);
                Date two = strToDate(endTime, format);
                long time1 = one.getTime(), time2 = two.getTime(), diff;
                if(time1 < time2) {
                    diff = time2 - time1;
                } else {
                    diff = time1 - time2;
                }
                day = diff / (24 * 60 * 60 * 1000);
                res = StringUtil.objToInteger(day);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 两个时间相差多少天
     * @param startTime 时间参数 1 格式：1990-01-01
     * @param endTime 时间参数 2 格式：2009-01-01
     * @return int 返回值为：例:1(天)
     */
    public static int getDistanceDay(Date startTime, Date endTime) {
        int res = 0;
        if(null != startTime && null != endTime) {
            long day = 0;
            try {
                long time1 = startTime.getTime(), time2 = endTime.getTime(), diff;
                if(time1 < time2) {
                    diff = time2 - time1;
                } else {
                    diff = time1 - time2;
                }
                day = diff / (24 * 60 * 60 * 1000);
                res = StringUtil.objToInteger(day);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 两个时间相差多少小时多少分
     * @param startTime 时间参数 1 格式：1990-01-01 12:00:00
     * @param endTime 时间参数 2 格式：2009-01-01 12:00:00
     * @return double 返回值为：例:9.5(小时)
     */
    public static double getDistanceHour(String startTime, String endTime) {
        double res = 0.0;
        startTime = StringUtil.trim(startTime);
        endTime = StringUtil.trim(startTime);
        if(!"".equals(startTime) && !"".equals(endTime)) {
            try {
                Date one = strToDate(startTime, null);
                Date two = strToDate(endTime, null);
                long time1 = one.getTime(), time2 = two.getTime(), diff;
                if(time1 < time2) {
                    diff = time2 - time1;
                } else {
                    diff = time1 - time2;
                }
                res=diff/(1000*60.0*60.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 两个时间相差多少小时多少分
     * @param startTime 时间参数 1 格式：1990-01-01 12:00:00
     * @param endTime 时间参数 2 格式：2009-01-01 12:00:00
     * @return string 返回值为：例:9.5(小时)
     */
    public static double getDistanceHour(Date startTime, Date endTime) {
        double res = 0.0;
        if(null != startTime && null != endTime) {
            long day = 0, hour = 0, min = 0;
            try {
                long time1 = startTime.getTime(), time2 = endTime.getTime(), diff;
                if(time1 < time2) {
                    diff = time2 - time1;
                } else {
                    diff = time1 - time2;
                }
                day = diff / (24 * 60 * 60 * 1000);
                hour = (diff / (60 * 60 * 1000) - day * 24);
                min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60) / 10;
                hour = day > 0 ? hour + (day * 24) : hour;
                String hours = hour + "." + min;
                res = StringUtil.objToDouble(hours);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static String getToday(){
        String today = StringUtil.trimObject(LocalDate.now());
        return today;
    }

    public static Date getToday(String format){
        format = StringUtil.trim(format,FORMAT_YEAR_MONTH_DAY);
        Date today = strToDate(getDateStr(new Date()),format);
        return today;
    }


    public static boolean isConsistent(Date date1, Date date2) {
        boolean flag = false;
        if(date1.getTime() == date2.getTime()) {
            flag = true;
        }
        return flag;
    }

    /**
     *
     * @param date1
     * @param date2
     * @param condition 算数运算符  例: > < = >= <=
     * @return
     */
    public static boolean compareDate(String date1, String date2, String condition) {

        boolean flag = false;
        try {
            date1 = StringUtil.trim(date1);
            date2 = StringUtil.trim(date2);
            condition = StringUtil.trim(condition);
            if(!"".equals(date1) && !"".equals(date2) && !"".equals(condition)) {
                Date dt1 = strToDate(date1, FORMAT_YEAR_MONTH_DAY);
                Date dt2 = strToDate(date2, FORMAT_YEAR_MONTH_DAY);
                if(condition.equals(">")) {
                    if (dt1.getTime() > dt2.getTime()) {
                        flag = true;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        flag = false;
                    }
                }else if(condition.equals("<")){
                    if (dt1.getTime() < dt2.getTime()) {
                        flag = true;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        flag = false;
                    }
                }else if(condition.equals(">=")){
                    if (dt1.getTime() >= dt2.getTime()) {
                        flag = true;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        flag = false;
                    }
                }else if(condition.equals("<=")){
                    if (dt1.getTime() <= dt2.getTime()) {
                        flag = true;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        flag = false;
                    }
                }else if(condition.equals("=")){
                    if (dt1.getTime() == dt2.getTime()) {
                        flag = true;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        flag = false;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        //System.out.println(limitCallTime());
    }
}
