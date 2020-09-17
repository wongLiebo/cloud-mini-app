package com.mini.cloud.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	public static boolean isAuthc(String val,String regex) {
		if(val.trim().equals(regex.trim())) {
			return true;
		}
		String p_str1=regex.replaceAll("\\*\\*", "#B#");
		String p_str2=p_str1.replaceAll("\\*", "#A#");
		String p_str3=p_str2.replaceAll("#B#", ".*");
		String p_str4=p_str3.replaceAll("#A#", ".[^/]*");
//		System.out.println(p_str4);
        Pattern pattern = Pattern.compile(p_str4);  
        Matcher matcher = pattern.matcher(val);  
		return  matcher.matches();
	}
	
	
}
