package com.nongyeos.loan.base.util;

import org.apache.commons.lang3.StringUtils;

public class AppNull {
	public static String s2SNull(String arg){
		return StringUtils.isEmpty(arg)?null:arg;
	}
	
	public static Integer s2INull(String arg){
		return StringUtils.isEmpty(arg)?null:new Integer(arg);
	}
	
	public static String o2SNotNull(Object arg){
		return arg==null?"":arg.toString();
	}
	
}
