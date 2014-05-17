package com.zorbit.utils.commonz;

import static com.zorbit.utils.commonz.FormatUtils.dateString;
import static com.zorbit.utils.commonz.FormatUtils.toDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class DataUtils {
    /**
     * 生成UUID
     * @return
     * @author Biller Zou
     */
    public static String newUUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 数字自动补0填充
     * @param num 数字
     * @param zero 补0填充的位数
     * @return
     */
    public static String autoZero(int num, int zero) {
        return String.format("%1$0" + zero + "d", num);
    }
    
    /**
     * 验证是否是电子邮箱
     * @param str
     * @return
     * @author Biller Zou
     */
    public static boolean isEmail(String str) {
        if (null == str) return false;
        return str.matches("^[\\w&&[^_]]+([\\.\\-_][\\w&&[^_]]+)*@([\\w&&[^_]]+[\\.\\-_]?)+[\\w&&[^_]]+\\.[A-Za-z]{2,5}$");
        
    }
    
    /**
     * 判断字符串是否为正整数
     * @param code
     * @return 如果是正整数则返回true，否则返回false
     * @author Biller Zou
     */
    public static boolean isUInteger(String code) {
        if (null == code) return false;
        return code.matches("^\\d+");
    }
    
    /**
     * 判断字符串是否为整数
     * @param code
     * @return 如果是整数则返回true，否则返回false
     * @author Biller Zou
     */
    public static boolean isInteger(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+");
    }
    
    /**
     * 判断字符串是否为正浮点数
     * @param code
     * @return 如果是正浮点数则返回true，否则返回false
     * @author Biller Zou
     */
    public static boolean isUFloat(String code) {
        if (null == code) return false;
        return code.matches("^\\d*(\\.\\d+)?");
    }
    
    /**
     * 判断字符串是否为浮点数
     * @param code
     * @return 如果是浮点数则返回true，否则返回false
     * @author Biller Zou
     */
    public static boolean isFloat(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d*(\\.\\d+)?");
    }
    
    /**
     * 判断字符串是否为空或空字符串
     * @param str 需要判断的字符串
     * @param trim 是否需要忽略两边空白后判断
     * @return 当字符串为空或为空字符串或在trim=true时str.trim()后为空字符串时，返回true，否则返回false
     * @author Biller Zou
     */
    public static boolean isNullOrEmpty(String str, boolean trim) {
        if (str == null) return true;
        if (str.isEmpty() || (trim && str.trim().isEmpty())) return true;
        return false;
    }
    
    /**
     * 判断字符串是否为空或空字符串（忽略两边空白）<br>
     * 同DataUtils.isNullOrEmpty(str, true)
     * 
     * <pre>
     * DataUtils.isNullOrEmpty(null) = true
     * DataUtils.isNullOrEmpty("") = true
     * DataUtils.isNullOrEmpty(" ") = true
     * DataUtils.isNullOrEmpty("12345") = false
     * DataUtils.isNullOrEmpty(" 12345 ") = false
     * </pre>
     * @param str 需要判断的字符串
     * @return 当字符串为空或在str.trim()后为空字符串时，返回true，否则返回false
     * @author Biller Zou
     */
    public static boolean isNullOrEmpty(String str) {
        return isNullOrEmpty(str, true);
    }
    
    /**
     * 获取非空字符串
     * @param value 需要处理判断的字符串
     * @param defaultValue 默认字符串
     * @param trim 是否需要忽略value的两边空白
     * @return 当value为null时，返回defaultValue，否则根据trim判断返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author Biller Zou
     */
    public static String defaultString(String value, String defaultValue, boolean trim) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return trim ? value.trim() : value;
    }
    
    /**
     * 获取非空字符串
     * @param value 需要处理判断的字符串
     * @param defaultValue 默认字符串
     * @return 当value为null时，返回defaultValue，否则返回value.trim()的返回值
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author Biller Zou
     */
    public static String defaultString(String value, String defaultValue) {
        return defaultString(value, defaultValue, true);
    }
    
    /**
     * 获取非空字符串
     * @param value 需要处理判断的字符串
     * @return 当value为null时，返回""，否则返回value.trim()的返回值
     * @author Biller Zou
     */
    public static String defaultString(String value) {
        return defaultString(value, "", true);
    }
    
    /**
     * 获取非空Double
     * @param value 需要处理判断的Double
     * @param defaultValue 默认Double值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author Biller Zou
     */
    public static Double defaultDouble(Double value, Double defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Double
     * @param value 需要处理判断的Double
     * @return 当value为null时，返回0.0D，否则返回value
     * @author Biller Zou
     */
    public static Double defaultDouble(Double value) {
        return defaultDouble(value, 0.0D);
    }
    
    /**
     * 获取非空Float
     * @param value 需要处理判断的Float
     * @param defaultValue 默认Float值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author Biller Zou
     */
    public static Float defaultFloat(Float value, Float defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Float
     * @param value 需要处理判断的Float
     * @return 当value为null时，返回0.0F，否则返回value
     * @author Biller Zou
     */
    public static Float defaultFloat(Float value) {
        return defaultFloat(value, 0.0F);
    }
    
    /**
     * 获取非空Integer
     * @param value 需要处理判断的Integer
     * @param defaultValue 默认Integer值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author Biller Zou
     */
    public static Integer defaultInteger(Integer value, Integer defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Integer
     * @param value 需要处理判断的Integer
     * @return 当value为null时，返回0，否则返回value
     * @author Biller Zou
     */
    public static Integer defaultInteger(Integer value) {
        return defaultInteger(value, 0);
    }
    
    /**
     * 获取非空Long
     * @param value 需要处理判断的Long
     * @param defaultValue 默认Long值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author Biller Zou
     */
    public static Long defaultLong(Long value, Long defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Long
     * @param value 需要处理判断的Long
     * @return 当value为null时，返回0L，否则返回value
     * @author Biller Zou
     */
    public static Long defaultLong(Long value) {
        return defaultLong(value, 0L);
    }
    
    /**
     * 将指定字符串转换为Double对象
     * @param value 需要转换的字符串
     * @return 如果value为null，或格式不是Double类型，返回0.0D，否则返回value内容的Double值
     * @author Biller Zou
     */
    public static Double toDouble(String value) {
        if (isFloat(value)) {
            return Double.valueOf(value);
        } else {
            return 0.0D;
        }
    }
    
    /**
     * 将指定字符串转换为Integer对象
     * @param value 需要转换的字符串
     * @return 如果value为null，或格式不是Integer类型，返回0，否则返回value内容的Integer值
     * @author Biller Zou
     */
    public static Integer toInteger(String value) {
        if (isInteger(value)) {
            return Integer.valueOf(value);
        } else {
            return 0;
        }
    }
    
    /**
     * 将指定日期的时间单位清零后返回
     * @param date 日期对象
     * @return 返回时间单位被清零后的date，即日期信息为 <b>yyyy-MM-dd 00:00:00</b>
     * @throws IllegalArgumentException date为空时抛出
     * @author Biller Zou
     */
    public static Date clearDateTime(Date date) {
        if (date == null) throw new IllegalArgumentException("date不允许为空");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 将当前日期的时间清零后返回
     * @return 返回时间单位被清零后的当前日期，即日期信息为 <b>yyyy-MM-dd 00:00:00</b>
     * @author Biller Zou
     */
    public static Date clearDateTime() {
        return clearDateTime(new Date());
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param cal1 第一个日期
     * @param cal2 第二个日期
     * @return 相差天数，若返回0，则表示同一天
     * @throws IllegalArgumentException cal1或cal2为空时抛出
     * @author Biller Zou
     */
    public static int dayDiff(Calendar cal1, Calendar cal2) {
        if (cal1 == null) throw new IllegalArgumentException("cal1不允许为空");
        if (cal2 == null) throw new IllegalArgumentException("cal2不允许为空");
        Calendar c = Calendar.getInstance();
        c.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
        long millis1 = c.getTimeInMillis();
        c.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
        long millis2 = c.getTimeInMillis();
        return Long.valueOf(Math.abs(millis1 - millis2) / 86400000).intValue();
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 相差天数，若返回0，则表示同一天
     * @throws IllegalArgumentException date1或date2为空时抛出
     * @author Biller Zou
     */
    public static int dayDiff(Date date1, Date date2) {
        if (date1 == null) throw new IllegalArgumentException("date1不允许为空");
        if (date2 == null) throw new IllegalArgumentException("date2不允许为空");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return dayDiff(c1, c2);
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param cal 第一个日期
     * @param date 第二个日期
     * @return 相差天数，若返回0，则表示同一天
     * @throws IllegalArgumentException cal或date为空时抛出
     * @author Biller Zou
     */
    public static int dayDiff(Calendar cal, Date date) {
        if (cal == null) throw new IllegalArgumentException("cal不允许为空");
        if (date == null) throw new IllegalArgumentException("date不允许为空");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return dayDiff(cal, c);
    }
    
    /**
     * 计算给出的日期相关sum天的日期
     * @param date 指定日期
     * @param sum 相差天数，负数就是前几天
     * @return 相差sum天的日期
     * @throws IllegalArgumentException date为空时抛出
     * @author Biller Zou
     */
    public static Date dayDiff(Date date, int sum) {
        if (date == null) throw new IllegalArgumentException("date不允许为空");
        return new Date(date.getTime() + (sum * 86400000l));
    }
    
    /**
     * 计算给出的日期相关sum天的日期字符串
     * @param date 指定日期 <b>yyyy-MM-dd</b> 或 <b>yyyy/MM/dd</b>
     * @param sum 相差天数，负数就是前几天
     * @return 相差sum天的日期 <b>yyyy-MM-dd</b>
     * @throws IllegalArgumentException date格式错误
     * @author Biller Zou
     */
    public static String dayDiff(String date, int sum) {
        Date d = toDate(date);
        if (d == null) throw new IllegalArgumentException("date格式错误");
        return dateString(dayDiff(d, sum));
    }
    
    /**
     * 根据给出星期规则从指定日期范围中获得所有符合规则的日期集合<br>
     * 如指定周一，周二，则从指定日期范围中，挑出所有周一和周二的日期
     * @param week 日期规则 一个包含1-7的字符串，1-7分别对应周一至周日，如：1347或1,3,4,7
     * @param beginDate 开始日期
     * @param endDate 结束日期（含）
     * @return 符合week指定的日期集合
     * @author Biller Zou
     */
    public static List<Date> weekDate(String week, Date beginDate, Date endDate) {
        List<Date> datelist = new ArrayList<Date>();
        Calendar begdate = Calendar.getInstance();
        Calendar enddate = Calendar.getInstance();
        begdate.setTime(beginDate);
        enddate.setTime(endDate);
        int weekday;
        while (begdate.before(enddate) || begdate.equals(enddate)) {
            weekday = begdate.get(Calendar.DAY_OF_WEEK) - 1;
            weekday = weekday == 0 ? 7 : weekday;
            if (week.contains(String.valueOf(weekday))) {
                datelist.add(begdate.getTime());
            }
            begdate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return datelist;
    }
    
    /**
     * 根据给出星期规则从指定日期范围中获得所有符合规则的日期集合<br>
     * 如指定周一，周二，则从指定日期范围中，挑出所有周一和周二的日期
     * @param week 日期规则 一个包含1-7的字符串，1-7分别对应周一至周日，如：1347或1,3,4,7
     * @param beginDate 开始日期 <b>yyyy-MM-dd</b>
     * @param endDate 结束日期（含） <b>yyyy-MM-dd</b>
     * @return 符合week指定的日期集合
     * @throws IllegalArgumentException beginDate或endDate为空或不符合指定格式时抛出
     * @author Biller Zou
     */
    public static List<Date> weekDate(String week, String beginDate, String endDate) {
        Date begdate = toDate(beginDate);
        Date enddate = toDate(endDate);
        if (begdate == null) throw new IllegalArgumentException("begdate格式错误");
        if (enddate == null) throw new IllegalArgumentException("enddate格式错误");
        return weekDate(week, begdate, enddate);
    }
    
    /**
     * 获取字符串字节长度，针对数据库保存时准确的判断字符长度
     * @param value 要判断的字符(半角字母、数字、英文符号算1个字符，其他算2个字符)
     * @return 字符串的字节总长度
     * @author Biller Zou
     */
    public static int bytesLength(String value) {
        return bytesLength(value, 2);
    }
    
    /**
     * 获取字符串字节长度，针对数据库保存时准确的判断字符长度
     * @param value 要判断的字符(半角字母、数字、英文符号算1个字符，其他算repLen指定的长度字符)
     * @param repLen 非半角字母数字等符号换算为多少长度
     * @return 字符串的字节总长度
     * @author Biller Zou
     */
    public static int bytesLength(String value, int repLen) {
        if (value == null) return 0;
        StringBuilder len = new StringBuilder();
        for (int i = 0; i < repLen; i++) {
            len.append("*");
        }
        return value.replaceAll("[^\\x00-\\xff]", len.toString()).length();
    }
    
    /**
     * 判断是否UUID字符串方法
     * @param uid
     * @return 如果uid为UUID字符串则返回true，否则返回false
     * @author Biller Zou
     */
    public static boolean isUid(String uid) {
        if (null == uid) return false;
        return uid.matches("^[\\w&&[^_]]{4}([\\w&&[^_]]{4}-){4}[\\w&&[^_]]{12}");
    }
    
    /**
     * 生成随机字符串（仅含数字及大小写字母）
     * @param length 随机字符串长度
     * @return 随机字符串
     * @author Biller Zou
     */
    public static String randomString(int length) {
        return randomString("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", length);
    }
    
    /**
     * 生成随机字符串方法
     * 
     * <pre>
     * 示例
     * randomString("abc123ABC", 3) = "2Ba" 
     * randomString("#$%UZefop", 5) = "%Zfe#"
     * </pre>
     * @param range 随机字符串候选值
     * @param length 随机字符串长度
     * @return 随机字符串
     * @author Biller Zou
     */
    public static String randomString(String range, int length) {
        Random rd = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(range.charAt(rd.nextInt(range.length())));
        }
        return result.toString();
    }
    
    /**
     * 验证身份证是否有效
     * @param card
     * @return 如果是有效身份证，则返回true，否则返回false
     * @author Liu Shiyu
     */
    public static boolean convertCardID(String card) {
        if (null == card) return false;
        int[] xx = {2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7};
        char[] yy = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int mm = 0;
        if (card.length() == 15) {
            if (getBirthday(card))
                return true;
            else
                return false;
        } else if (card.length() == 18) {
            int[] gg = new int[18];
            for (int i = 1; i < 18; i++) {
                int j = 17 - i;
                gg[i - 1] = Integer.parseInt(card.substring(j, j + 1));
            }
            for (int i = 0; i < 17; i++) {
                mm += xx[i] * gg[i];
            }
            mm = mm % 11;
            char c = card.charAt(17);
            if (c == yy[mm] && getNewBirthday(card))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }
    
    /**
     * 15位身份证转18位身份证号码
     * @param id 15位身份证号码
     * @return 如果参数不符合要求则返回null
     * @author Biller Zou
     */
    public static String convertId15to18(String id) {
        if (null == id || id.equals("") || id.length() != 15) return null;
        int[] W = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        String[] A = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        int i, j, s = 0;
        String newid = id.substring(0, 6) + "19" + id.substring(6, id.length());
        for (i = 0; i < newid.length(); i++) {
            j = Integer.parseInt(newid.substring(i, i + 1)) * W[i];
            s += j;
        }
        s = s % 11;
        newid += A[s];
        return newid;
    }
    
    /**
     * 判断新身份证的出身日期是否有效
     * @param card
     * @return 如果有效则返回true，否则返回false
     * @author Liu Shiyu
     */
    public static boolean getNewBirthday(String card) {
        int y = Integer.parseInt(card.substring(6, 10));
        int m = Integer.parseInt(card.substring(10, 12));
        int d = Integer.parseInt(card.substring(12, 14));
        if (y < 1900 || m < 1 || m > 12 || d < 1 || d > 31 || ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) || (m == 2 && ((y % 4 > 0 && d > 28) || d > 29))) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 判断旧身份证的出身日期是否有效
     * @param card
     * @return 如果有效则返回true，否则返回false
     * @author Liu Shiyu
     */
    public static boolean getBirthday(String card) {
        int y = Integer.parseInt(card.substring(6, 8));
        int m = Integer.parseInt(card.substring(8, 10));
        int d = Integer.parseInt(card.substring(10, 12));
        if (m < 1 || m > 12 || d < 1 || d > 31 || ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) || (m == 2 && (((y + 1900) % 4 > 0 && d > 28) || d > 29))) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 从身份证中获得相关用户信息
     * @param id 身份证
     * @return <b>String[]</b> 长度为3，对应长度，内容分别为性别，生日和籍贯。
     *         若身份证号码错误则返回一个包含三个零字节字符串的数组
     * @author Biller Zou
     */
    public static String[] getInfoFromId(String id) {
        if (!convertCardID(id)) return new String[]{"", "", ""};
        if (id.length() == 15) id = convertId15to18(id);
        String[] info = new String[3];
        info[0] = Integer.valueOf(((Character) id.charAt(16)).toString()) % 2 == 0 ? "女" : "男";
        info[1] = id.substring(6, 10) + "-" + id.substring(10, 12) + "-" + id.substring(12, 14);
        if (id.substring(0, 4).equals("4403")) {
            info[2] = "深圳";
        } else if (id.substring(0, 4).equals("5102")) {
            info[2] = "重庆";
        } else {
            Map<String, String> nativeList = new HashMap<String, String>();
            nativeList.put("11", "北京");
            nativeList.put("12", "天津");
            nativeList.put("13", "河北");
            nativeList.put("14", "山西");
            nativeList.put("15", "内蒙古");
            nativeList.put("21", "辽宁");
            nativeList.put("22", "吉林");
            nativeList.put("23", "黑龙江");
            nativeList.put("31", "上海");
            nativeList.put("32", "江苏");
            nativeList.put("33", "浙江");
            nativeList.put("34", "安徽");
            nativeList.put("35", "福建");
            nativeList.put("36", "江西");
            nativeList.put("37", "山东");
            nativeList.put("41", "河南");
            nativeList.put("42", "湖北");
            nativeList.put("43", "湖南");
            nativeList.put("44", "广东");
            nativeList.put("45", "广西");
            nativeList.put("46", "海南");
            nativeList.put("50", "重庆");
            nativeList.put("51", "四川");
            nativeList.put("52", "贵州");
            nativeList.put("53", "云南");
            nativeList.put("54", "西藏");
            nativeList.put("61", "陕西");
            nativeList.put("62", "甘肃");
            nativeList.put("63", "青海");
            nativeList.put("64", "宁夏");
            nativeList.put("65", "新疆");
            nativeList.put("71", "台湾");
            nativeList.put("81", "香港");
            nativeList.put("82", "澳门");
            info[2] = nativeList.get(id.substring(0, 2));
        }
        return info;
    }
}
