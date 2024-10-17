package com.panda.cdc.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理公共类
 *
 * @author muxh
 * @Type DateUtils
 * @Desc 日期处理
 * @date 2021/09/10
 * @Version V1.0
 */
public class DateUtils {

    private static final Log logger = LogFactory.getLog(DateUtils.class);

    public static final String FORMAT_DATE_YM = "yyyy-MM";

    public static final String FORMAT_DATE_YMD_CHINESE = "yyyy年MM月dd日";

    public static final String FORMAT_DATE_YMD = "yyyy-MM-dd";

    public static final String FORMAT_DATE_YYMMDD = "yyyyMMdd";

    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_STR_FORMAT_YMDHMS = "yyyyMMddHHmmss";

    public static final String DATE_STR_FORMAT_YMDHMSS = "yyyyMMddHHmmssSSS";

    public static final String DATE_STR_FORMAT_YMDHM = "yyyyMMddHHmm";

    public static final String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_MDHM = "HHmm";

    public static final String DATE_FORMAT_MDHM2 = "HH:mm";

    public static final String DATE_FORMAT_MDHMS = "HH:mm:ss";

    public static final String DATE_FORMAT_YMDHMSTZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 一周星期数组
     */
    private final static String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 上下午数组
     */
    private final static String apms[] = {"上午", "下午", "晚上", "全天"};

    /**
     * 用hh:mm 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getYMDHMSSDateString(Date d) {
        SimpleDateFormat destsmf = new SimpleDateFormat(DATE_STR_FORMAT_YMDHMSS);
        return destsmf.format(d);
    }

    /**
     * 用hh:mm 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getHmDateString(Date d) {
        SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_MDHM);
        return destsmf.format(d);
    }

    /**
     * 用hh:mm 来格式化日期
     *
     * @param d
     * @return String
     * @author zhanggn
     */
    public static String getHmDateString2(Date d) {
        SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_MDHM2);
        return destsmf.format(d);
    }

    /**
     * 用yyyy-MM-dd hh:mm:sss 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getYmdhmssDateString(Date d) {
        SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        return destsmf.format(d);
    }

    /**
     * 用yyyy-MM-dd hh:mm:ss 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getYmdhmssDateString(String d) {
        try {
            String date = d.replace("T", "");
            Date parse = new SimpleDateFormat(DATE_STR_FORMAT_YMDHMS).parse(date);
            String now = new SimpleDateFormat(DATE_FORMAT_YMDHMS).format(parse);
            return now;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 用yyyy-MM-dd hh:mm 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getYmdhmDateString(String d) {
        try {
            String date = d.replace("T", "");
            Date parse = new SimpleDateFormat(DATE_STR_FORMAT_YMDHM).parse(date.substring(0, 12));
            String now = new SimpleDateFormat(DATE_FORMAT_YMDHM).format(parse);
            return now;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 用yyyy-MM-dd 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getDateString(String d) {
        try {
            Date parse = new SimpleDateFormat(FORMAT_DATE_YYMMDD).parse(d.substring(0, 8));
            String now = new SimpleDateFormat(FORMAT_DATE_YMD).format(parse);
            return now;
        } catch (Exception e) {
            logger.error("格式化日期出错", e);
        }
        return StringUtils.EMPTY;
    }

    public static String getSimpleDateString(String a) {
        try {
            Date parseDate = new SimpleDateFormat(DATE_FORMAT_YMDHMS).parse(a.substring(0, 19));
            String nowDate = new SimpleDateFormat(DATE_FORMAT_YMDHM).format(parseDate);
            return nowDate;
        } catch (Exception e) {
            logger.error("格式化日期出错", e);
        }
        return StringUtils.EMPTY;
    }


    /**
     * 用yyyy-MM-dd hh:mm:sss 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getYmdhmDateString(Date d) {
        SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_YMDHM);
        return destsmf.format(d);
    }

    /**
     * 取得当前时间 yyyy-MM-dd
     *
     * @return String
     */
    public static String getAppDate() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_YMD);
        return sf.format(date);
    }

    /**
     * 取得当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat(DateUtils.DATE_FORMAT_YMDHMS);
        return sdFormatter.format(nowTime);
    }

    /**
     * 取得昨天日期
     *
     * @return String
     */
    public static String getYesterdayString() {
        Date date = addDay(new Date(), -1);
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_YMD);
        return sf.format(date);
    }

    /**
     * 取得昨天yyyy-mm-dd HH:mm:ss日期
     *
     * @return String
     */
    public static String getYesterdayTimeString() {
        Date date = addDay(new Date(), -1);
        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        return sf.format(date);
    }

    /**
     * 今天前后N天的日期 昨天-1 今天0 明天1
     *
     * @return String
     */
    public static String getBeforeYesterdayString(int amount) {
        Date date = addDay(new Date(), amount);
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_YMD);
        return sf.format(date);
    }

    /**
     * 在当前日基础增加天数
     *
     * @param date
     * @param amount
     * @return Date
     * @author wangbiao
     */
    public static Date addDay(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }

    /**
     * 在当前日基础增加月
     *
     * @param date
     * @param amount
     * @return Date
     * @author wangbiao
     */
    public static Date addMonth(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return calendar.getTime();
    }

    /**
     * 取得日期，格式化：yyyy-MM-dd 0点0分
     *
     * @param date
     * @return String
     */
    public static String getDateString(Date date) {
        if (null == date) {
            return StringUtils.EMPTY;
        }
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_YMD);
        return sf.format(date);
    }

    /**
     * 用yyyy-MM-dd 来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getDateString(Date d, String template) {
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(template);
            return destsmf.format(d);
        } catch (Exception e) {
            logger.error("格式化日期出错", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 格式化日期
     *
     * @param d
     * @return String
     * @author wangjie
     */
    public static Date getStringTODate(String d, String template) {
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(template);
            return destsmf.parse(d);
        } catch (Exception e) {
            logger.error("格式化日期出错", e);
        }
        return null;
    }

    /**
     * 判断2个日期是否在同一天
     *
     * @param d1
     * @param d2
     * @return true是，false否
     */
    public static boolean isInOneDay(Date d1, Date d2) {
        return formatDate(d1, FORMAT_DATE_YMD).equals(formatDate(d2, FORMAT_DATE_YMD));
    }

    /**
     * 将一个指定的日期格式化成指定的格式
     *
     * @param date    指定的日期
     * @param pattern 指定的格式
     * @return 格式化好后的日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }


    /**
     * 去掉时分秒
     *
     * @param date
     * @return Date
     */
    public static Date getYmdTime(Date date) {
        if (date == null) {
            return (new Date());
        }
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        Date convertTime = day.getTime();
        return convertTime;
    }

    /**
     * 时间转换
     *
     * @param s
     * @param p
     * @return
     */
    public static Date parseDateFromString(String s, String p) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            return new SimpleDateFormat(p).parse(s);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 用yyyy-MM-dd来格式化日期字符串
     *
     * @param s
     * @return 如果s==null或者格式异常，返回null
     */
    public static Date getYyyyMMddConnectDate(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 用yyyy-MM-dd HH:mm:ss来格式化日期字符串
     *
     * @param s
     * @return 如果s==null或者格式异常，返回null
     */
    public static Date getYyyyMMddHHConnectDate(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            return new SimpleDateFormat(DATE_FORMAT_YMDHMS).parse(s);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 用yyyy-MM-dd HH:mm来格式化日期字符串
     *
     * @param s
     * @return 如果s==null或者格式异常，返回null
     */
    public static Date getYyyyMMddHHmmDate(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            return new SimpleDateFormat(DATE_FORMAT_YMDHM).parse(s);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 用yyyy-MM-dd HH:mm:ss来格式化日期字符串
     *
     * @param s
     * @return 如果s==null或者格式异常，返回null
     */
    public static Date getYyyyMMddHHmmssDate(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            return new SimpleDateFormat(DATE_FORMAT_YMDHMS).parse(s);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }

    public static Date getDateFirstTime(Integer monthLen) {
        try {
            Date dNow = new Date(); // 当前时间
            Date dBefore = new Date();
            Calendar calendar = Calendar.getInstance(); // 得到日历
            calendar.setTime(dNow);// 把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -monthLen); // 设置为前一天
            dBefore = calendar.getTime(); // 得到前一天的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
            String defaultStartDate = sdf.format(dBefore) + " 00:00:00"; // 格式化前一天
            return sdf2.parse(defaultStartDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDateLastTime(Integer monthLen) {
        try {
            Date dNow = new Date(); // 当前时间
            Date dBefore = new Date();
            Calendar calendar = Calendar.getInstance(); // 得到日历
            calendar.setTime(dNow);// 把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -monthLen); // 设置为前一天
            dBefore = calendar.getTime(); // 得到前一天的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
            String defaultStartDate = sdf.format(dBefore) + " 23:59:59"; // 格式化前一天
            return sdf2.parse(defaultStartDate);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 用yyyyMMdd来格式化日期
     *
     * @param d
     * @return String
     * @author qinying
     */
    public static String getYmdDateString(Date d) {
        SimpleDateFormat destsmf = new SimpleDateFormat(FORMAT_DATE_YYMMDD);
        return destsmf.format(d);
    }

    /**
     * 获取周一
     *
     * @return String
     */
    public static Date getThisWeekStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date eTime = calendar.getTime();

        return eTime;
    }

    /**
     * 获取上周日日期
     *
     * @return String
     */
    public static Date getOneWeekEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date eTime = calendar.getTime();

        return eTime;
    }

    /**
     * 获取上周一日期
     *
     * @return String
     */
    public static Date getOneWeekStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date eTime = calendar.getTime();

        return eTime;
    }


    /**
     * 获取上午、中午还是下午
     *
     * @param arg
     * @return
     */
    public static String getSZX(Date d) {
        if (null == d) {
            return StringUtils.EMPTY;
        }
        SimpleDateFormat destsmf = new SimpleDateFormat("HHmm");
        String hmString = destsmf.format(d);
        Integer hm = Integer.valueOf(hmString);
        if (hm > 0 && hm <= 1200) {
            hmString = "上午";
        } else if (hm > 1200 && hm <= 1800) {
            hmString = "下午";
        } else {
            hmString = "晚上";
        }
        return hmString;
    }

    /**
     * 根据日期获得星期几
     *
     * @param date
     * @return
     */
    public static String getWeekDayString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayNames[dayOfWeek - 1];
    }

    /**
     * 获取今天是周几
     *
     * @param date
     * @return 1、周一 2、周二 以此类推
     */
    public static int getWeekDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }

    /**
     * @return Date yyyy-MM-dd
     * @throws ParseException
     */
    public static Date getyMdDate(String dateStr) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_YMD);
        return sf.parse(dateStr);
    }

    /**
     * 计算两个日期的差距(天数)，去掉时分秒
     *
     * @param date1
     * @param date2
     * @return Long
     */
    public static Long getDaysToLong(Date date1, Date date2) {
        Long d1 = getYmdTime(date1).getTime() / 1000;
        Long d2 = getYmdTime(date2).getTime() / 1000;
        return (d2 - d1) / (24 * 3600);
    }

    /**
     * 取得当前时间
     *
     * @return String
     */
    public static String getCurTime(String format) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * 取得当前时间
     *
     * @return Date
     */
    public static Date getCurDate(String format) {
        Date date = null;
        if (StringUtils.isBlank(format)) {
            logger.error("获取当前时间出错");
            return date;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String now = getCurTime(format);
        try {
            date = formatter.parse(now);
            return date;
        } catch (ParseException e) {
            logger.error("获取当前时间出错", e);
            return date;
        }
    }

    public static Date getParseDate(String s, String p) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            return new SimpleDateFormat(p).parse(s);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * @return Date yyyy-MM-dd
     * @throws ParseException
     */
    public static Date getYMdDate(String dateStr) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_YMD);
        return sf.parse(dateStr);
    }

    /**
     * 获取第二天
     */
    public static Date getNextday() {
        Date date = new Date();// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String dateString = formatter.format(date);
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            logger.error(e);
        }
        return date;
    }

    /**
     * 获取 date 下一天
     *
     * @param date
     * @return
     */
    public static Date getNextDay(String dateString) {
        Calendar calendar = new GregorianCalendar();
        Date date = DateUtils.getStringTODate(dateString, DateUtils.FORMAT_DATE_YMD);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        return date;
    }

    /**
     * 字符串转换为日期
     */
    public static Date getStringToDateTZ(String date) throws ParseException {
        return new SimpleDateFormat(DATE_FORMAT_YMDHMSTZ).parse(date);
    }

    /**
     * 用yyyyMMdd来格式化日期字符串
     *
     * @param s
     * @return 如果s==null或者格式异常，返回null
     */
    public static Date getYyyyMMddDate(String s) {
        if (s == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(s);
        } catch (ParseException e) {

            return null;
        }
    }

    /**
     * 获取日期指定的格式的字符串
     *
     * @param date
     * @param pattern
     * @return Date 对象类型字符串形式
     */
    public static String getFormatDate(Date date, String pattern) {
        if (null != date) {
            DateFormat dateFomat = new SimpleDateFormat(pattern);
            return dateFomat.format(date);
        }
        return "";
    }

    /**
     * 根据生日计算当前年龄
     *
     * @param brithday
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public static int getCurrentAgeByBirthdate(String brithday) {
        if (brithday == null) {
            logger.error("参数为空");
            return 0;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatDate = new SimpleDateFormat(FORMAT_DATE_YYMMDD);
            String currentTime = formatDate.format(calendar.getTime());
            Date today = formatDate.parse(currentTime);
            Date brithDay = formatDate.parse(brithday);

            return today.getYear() - brithDay.getYear();
        } catch (Exception e) {
            logger.error("计算年龄失败", e);
            return 0;
        }
    }

    /**
     * 计算就诊后天数
     *
     * @param clinicDate
     * @return String
     */
    public static Integer getTreatAfterDay(String clinicDate) {
        if (StringUtils.isNotBlank(clinicDate)) {
            try {
                return Integer.valueOf(String.valueOf(getDaysToInt(getStringToDate(clinicDate), new Date())));
            } catch (ParseException e) {
                logger.error(e);
            }
        }
        return null;
    }

    public static int getDaysToInt(Date date1, Date date2) {
        return Integer.valueOf(getDaysToLong(date1, date2).toString());
    }


    // 时间描述 1天是 24*3600*1000毫秒
    public static String getTimeDesc(Date date) {
        if (null == date) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        // 毫秒偏移量
        long t = (now.getTimeInMillis() - time.getTimeInMillis()) / 1000;
        // 昨天以前的，直接显示日期，如2012-01-08，如果有多余空间，显示日期及时间2012-01-08 09:20
        if ((t < 0)
                || (t > ((now.get(Calendar.HOUR_OF_DAY) + 24) * 3600 + now.get(Calendar.MINUTE) + 60 + now
                .get(Calendar.SECOND))) || (t < 0)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            return df.format(date);
        }

        // 1个小时以上，但还在今天内，显示为"今天 09:30"
        if (t > 3600) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            String str = df.format(date);
            int day_time = time.get(Calendar.DAY_OF_YEAR);// 评论的天
            int now_time = now.get(Calendar.DAY_OF_YEAR);// 当前天

            // 1个小时以上，但在昨天内，显示为"昨天11:59"
            if (day_time < now_time) {
                str = "昨天 ".concat(str);
            } else {
                str = "今天 ".concat(str);
            }
            return str;
        }
        // 1分钟到1个小时内，显示为"XX分钟前"
        if (t >= 60) {
            return t / 60 + "分钟前";
        }
        // 小于1分钟的为刚刚
        return "刚刚";
    }

    /**
     * 当前日期增加或减少几天 增加 正整数 减少 负整数
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getStatetime(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 获取当前时间之前或之后几小时 hour
     */
    public static String getTimeByHour(int hour) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }

    /**
     * Calendar 实现时间+ 分钟
     */
    public static String getTimeByMinute(int addnumber) {
        String str = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            //时间累计
            Calendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.MINUTE, addnumber);
            str = df.format(gc.getTime());
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * Calendar 实现时间+ 分钟
     */
    public static String addtime(String timeStr, String addnumber) {
        String str = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(timeStr);
            //时间累计
            Calendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.MINUTE, Integer.parseInt(addnumber));
            str = df.format(gc.getTime());
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 计算两个日期比较
     *
     * @param date1
     * @param date2
     * @return Long
     */
    public static Long getDaysBetween(Date date1, Date date2) {
        Long d1 = date1.getTime() / 1000;
        Long d2 = date2.getTime() / 1000;
        return d2 - d1;
    }

    /**
     * 字符串转换为日期
     */
    public static Date getStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }


}