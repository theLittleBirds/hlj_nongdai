package com.nongyeos.loan.base.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {


	private static final NumberFormat FORMATTER_GENERIC_NUMBER = NumberFormat
			.getNumberInstance();
	
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    public final static String COLON          = ":";
    public final static String COMMA          = ",";
    public final static String EMPTY          = "";
    public final static String UNDER_LINE     = "_";
    public final static String ENDL           = "\n";
    public final static String SLASH          = "/";
    public final static String BLANK          = " ";
    public final static String DOT            = ".";

    public final static String FILE_SEPARATOR = File.separator;
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 转换为字节数组
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }
    
    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs){
    	if (str != null){
        	for (String s : strs){
        		if (str.equals(trim(s))){
        			return true;
        		}
        	}
    	}
    	return false;
    }
    
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html){
		if (html == null){
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt){
		if (txt == null){
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
				"$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
				"$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
    
    /**
     * 如果不为空，则设置值
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)){
			target = source;
		}
	}
 
    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
    	StringBuilder result = new StringBuilder();
    	StringBuilder val = new StringBuilder();
    	String[] vals = split(objectString, ".");
    	for (int i=0; i<vals.length; i++){
    		val.append("." + vals[i]);
    		result.append("!"+(val.substring(1))+"?'':");
    	}
    	result.append(val.substring(1));
    	return result.toString();
    }

    public static boolean isUpperCase(String s) {
        boolean result = true;
        char ch;
        for (int i = 0; i < s.length(); i++){
            ch = s.charAt(i);
            if (!(ch >= 'A' && ch <= 'Z')){
                result = false;
            }
        }
        return result;
    }
    

    
    public static String toLowerCase(String sourceStr) {
        if (StringUtils.isNotBlank(sourceStr)){
            return sourceStr.toLowerCase();
        }
        return "";
    }
    
    public static String toUpperCase(String sourceStr) {
    	if (StringUtils.isNoneBlank(sourceStr)) {
    		return sourceStr.toUpperCase();
    	}
    	return "";
    }


	/**
	 * BigDecimal保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static String getBigDecimalScale2(BigDecimal value) {

		if (value == null) {
			return "0";
		}
		return new java.text.DecimalFormat("0.##").format(value);
	}

	/**
	 * 将指定的值转换成字符串
	 *
	 * @param _value 指定的值
	 * @return String 转换后的值
	 */
	public static String defaultString(int _value) {
		return String.valueOf(_value);
	}

	/**
	 * 将指定的值转换成字符串
	 *
	 * @param _value 指定的值
	 * @return String 转换后的值
	 */
	public static String defaultString(long _value) {
		return String.valueOf(_value);
	}

	/**
	 * 将指定的值转换成字符串
	 *
	 * @param _value 指定的值
	 * @return String 转换后的值
	 */
	public static String defaultString(BigDecimal _value) {
		return defaultString(_value, false);
	}

	public static String defaultString(BigDecimal _value, String _default) {
		if (_value == null) {
			return _default;
		}
		return defaultString(_value, false);
	}

	/**
	 * 将指定的值转换成字符串
	 *
	 * @param _value 指定的值
	 * @return String 转换后的值
	 */
	public static String defaultString(BigDecimal _value, boolean _isEditComma) {
		if (_value == null) {
			return "";
		}
		return _isEditComma ? FORMATTER_GENERIC_NUMBER.format(_value) : _value
				.toPlainString();
	}

	/**
	 * 将指定的值转换成字符串
	 *
	 * @param _value 指定的值
	 * @return String 转换后的值
	 */
	public static String defaultString(Object _value) {
		return _value == null ? "" : _value.toString();
	}
	

    /*
     * 生成随机文件名
     */
	public static String generateRandomFilename() {
        String randomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        int intHour = calCurrent.get(Calendar.HOUR_OF_DAY);
        int intMinute = calCurrent.get(Calendar.MINUTE);
        int intSecond = calCurrent.get(Calendar.SECOND);
        String now = String.valueOf(intYear) + String.valueOf(intMonth) + String.valueOf(intDay)
                + String.valueOf(intHour) + String.valueOf(intMinute) + String.valueOf(intSecond);
        randomFilename = now + String.valueOf(random > 0 ? random : (-1) * random);

        return randomFilename;
    }
	

	/**
	 * <b>截取指定字节长度的字符串，不能返回半个汉字</b>
	 * 例如：
	 * 如果网页最多能显示17个汉字，那么 length 则为 34
	 * StringTool.getSubString(str, 34);
	 *
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getSubString(String str, int length) {
		int count = 0;
		int offset = 0;
		String result = "";
		char[] c = str.toCharArray();
		boolean isAdd = false;
		if (c.length > length) {
			isAdd = true;
		}
		
		for (int i = 0; i < c.length; i++) {
			if (c[i] > 256) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				count++;
			}
			if (count == length) {
				result = str.substring(0, i + 1) ;
				break;
			}
			if ((count == length + 1 && offset == 2)) {
				result = str.substring(0, i);
				break;
			}
		}
		
		if (isAdd) {
			result = result + "...";
		}
		
		return result;
	}

	/**
	 * 生成length位随机数字字母字符串
	 *
	 * @param length
	 * @return
     */
	public static String createCharacter(int length) {
		char[] codeSeq = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
				'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9'};
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
			s.append(r);
		}
		return s.toString();
	}

    /**
     * 生成length位随机数字字符串
     *
     * @param length
     * @return
     */
    public static String createNumber(int length) {
        char[] codeSeq = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
            s.append(r);
        }
        return s.toString();
    }

	/**
	 * 删除字符串中所有空白
	 * @param str
	 * @return
	 */
	public static String trimAllBlank(String str) {
    	return isBlank(str) ? null : str.replaceAll("\\s*", "");
	}

	/**
	 * 空白字符串转换为"无"
	 * @param str
	 * @return
	 */
	public static String transNullToWu(String str) {
		return isBlank(str) ? "无" : str;
	}
}
