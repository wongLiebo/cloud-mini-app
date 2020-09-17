package com.mini.cloud.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import cn.hutool.core.util.RandomUtil;

public class ReferralCodeUtils {

	
	private static String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
	private static char[] m = model.toCharArray();

	/***/
	public static String referralCode4(List<String> codes) {
		return ReferralCodeUtils.referralCode(4, codes);
	}
	/***/
	public static String referralCode4() {
		return ReferralCodeUtils.referralCode(4, null);
	}
	/***/
	public static String referralCode6(List<String> codes) {
		return ReferralCodeUtils.referralCode(6, codes);
	}
	/***/
	public static String referralCode6() {
		return ReferralCodeUtils.referralCode(6,null);
	}
	
	private static String referralCode(int len ,List<String> codes) {
		String code=null;
		boolean flag=false;
		int i=0;
		do {
			code=ReferralCodeUtils.code(len);
			if(CollectionUtils.isEmpty(codes)) {
				flag=true;
				break;
			}else {
				if(!codes.contains(code)) {
					flag=true;
					break;
				}
			}
			i++;
		} while (i<5000);
		
		if(flag) {
			
		}
		return code;
	}
	
	private static String code(int len) {
		StringBuilder str=new StringBuilder();
		for (int i = 0; i < len; i++) {
			str.append(m[RandomUtil.randomInt(0, 34)]);
		}
		return str.toString();
	}
	
	
	
	
	public static void main(String[] args) {
		List<String> codes=new ArrayList<String>();
		codes.add("83Y5");
		codes.add("2RKU");
		codes.add("OUKB");
		codes.add("CW7Y");
		System.out.println(ReferralCodeUtils.referralCode4(codes));
	}
}
