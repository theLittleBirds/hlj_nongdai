/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.nongyeos.loan.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 日期处理
 * 
 * 
 * 2014年5月5日 下午12:00:00
 * flyfox 330627517@qq.com
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX = "yyyy-MM-dd";
	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX_YYYYMMDD = "yyyyMMdd";
	/**
	 * 默认的日期格式 .
	 */
    public static final String REGEX_YYYY_MM_DD_HH_MM_SS = "yyyyMMddHHmmss";
    /**
     * 默认的日期格式 .
     */
    public static final String DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT = new EPNDateFormat(DEFAULT_REGEX);
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS = new EPNDateFormat(
			DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT_YYYYMMDD = new EPNDateFormat(DEFAULT_REGEX_YYYYMMDD);
	private static Map<String, EPNDateFormat> formatMap = new HashMap<String, EPNDateFormat>();
	static {
		formatMap.put(DEFAULT_REGEX, DEFAULT_FORMAT);
		formatMap.put(DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS, DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS);
		formatMap.put(DEFAULT_REGEX_YYYYMMDD, DEFAULT_FORMAT_YYYYMMDD);
	}

	private DateUtils() {

	}

	/**
	 * 时间对象格式化成String ,等同于java.text.DateFormat.format();
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return 转化结果
	 */
	public static String format(java.util.Date date) {
		return DEFAULT_FORMAT.format(date);
	}

	/**
	 * 时间对象格式化成String ,等同于java.text.SimpleDateFormat.format();
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * @param regex
	 *            定义格式的字符串
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com    
	 * @return 转化结果
	 */
	public static String format(java.util.Date date, String regex) {
		return getDateFormat(regex).format(date);
	}

	private static EPNDateFormat getDateFormat(String regex) {
		EPNDateFormat fmt = formatMap.get(regex);
		if (fmt == null) {
			fmt = new EPNDateFormat(regex);
			formatMap.put(regex, fmt);
		}
		return fmt;
	}

	/**
	 * 尝试解析时间字符串 ,if failed return null;
	 * 
	 * @author wangp
	 * @since 2008.12.20
	 * @param time
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return
	 */
	public static Date parseByAll(String time) {
		Date stamp = null;
		if (time == null || "".equals(time))
			return null;
		Pattern p3 = Pattern.compile("\\b\\d{2}[.-]\\d{1,2}([.-]\\d{1,2}){0,1}\\b");
		if (p3.matcher(time).matches()) {
			time = (time.charAt(0) == '1' || time.charAt(0) == '0' ? "20" : "19") + time;
		}

		stamp = DateUtils.parse(time, "yyyy-MM-ddHH:mm:ss");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyyMMdd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年MM月dd日");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyyMM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年MM月");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年");
		return stamp;
	}

	/**
	 * 解析字符串成时间 ,遇到错误返回null不抛异常
	 * 
	 * @param source
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return 解析结果
	 */
	public static java.util.Date parse(String source) {
		try {
			return DEFAULT_FORMAT.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解析字符串成时间 ,遇到错误返回null不抛异常
	 * 
	 * @param source
	 * @param 格式表达式
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return 解析结果
	 */
	public static java.util.Date parse(String source, String regex) {
		try {
			EPNDateFormat fmt = getDateFormat(regex);
			return fmt.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 取得当前时间的Date对象 ;
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return
	 */
	public static Date getNowDate() {
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前时间字符串
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return
	 */
	public static String getNow() {
		return getNow(DEFAULT_REGEX);
	}
	
	/**
	 * 获取当前时间字符串
	 * 
	 * 2014年7月4日 下午11:47:10
	 * flyfox 330627517@qq.com
	 * @param regex 格式表达式
	 * @return
	 */
	public static String getNow(String regex) {
		return format(getNowDate(), regex);
	}

    /**
     * 获取Unix时间戳的系统时间
     *
     * @return 当前时间的Unix时间戳
     */
    public static long getSysTime() {
        return getUnixTime(new Date());
    }

	/**
	 * 得到指定时间的Unix时间戳
	 * 
	 * @return 当前时间的Unix时间戳
	 */
	public static long getUnixTime(Date date){
		Timestamp now = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date tempDate = null;
		Long result = 0L;
		try {
			tempDate = df.parse(String.valueOf(now));
			long s=tempDate.getTime();
			result = NumberUtils.toLong(StringUtils.substring(StrUtils.defaultString(s), 0, 10));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return result;
	}
	

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static long getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
        return (afterTime - beforeTime) / 1000 / 60 / 60 / 24;
    }

    /**
     * 获取两个日期之间的天数（before不算时间，after不足一天的算一天）
     * 如果before > after则返回负
     *
     * @param before
     * @param after
     * @return
     */
    public static int getDaysOfTwoDate(Date before, Date after) {
        Date beforeYYYYMMDD = parseDate(formatDate(before, "yyyy-MM-dd"));
        Date afterYYYYMMDD = parseDate(formatDate(after, "yyyy-MM-dd"));

        long beforeTime = beforeYYYYMMDD.getTime();
        long afterTime = afterYYYYMMDD.getTime();
        long days = (afterTime - beforeTime) / 1000 / 60 / 60 / 24;

        return (int) days;
    }

    /**
     * 通过Unix时间戳得到Date对象
     *
     * @param time unix时间戳
     * @return 日期对象
     */
    public static Date parseDateByTime(long time) {
        return new Date(time * 1000);
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

	/**
	 * 得到指定日期字符串 格式（dd）
	 */
	public static String getDay(Date date) {
		return formatDate(date, "dd");
	}

    /**
     * 转换日期 格式（年月日）
     *
     * @param date
     * @return
     */
    public static String formatYMD(Date date) {
        if (date == null) {
            return null;
        } else {
            return formatDate(date, "yyyy") + "年" + formatDate(date, "MM") + "月" + formatDate(date, "dd") + "日";
        }
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getDateStart(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}

class EPNDateFormat {
	private SimpleDateFormat instance;

	EPNDateFormat() {
		instance = new SimpleDateFormat(DateUtils.DEFAULT_REGEX);
		instance.setLenient(false);
	}

	EPNDateFormat(String regex) {
		instance = new SimpleDateFormat(regex);
		instance.setLenient(false);
	}

	synchronized String format(java.util.Date date) {
		if (date == null)
			return "";
		return instance.format(date);
	}

	synchronized java.util.Date parse(String source) throws ParseException {
		return instance.parse(source);
	}
}
