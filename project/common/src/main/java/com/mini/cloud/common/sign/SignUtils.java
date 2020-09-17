package com.mini.cloud.common.sign;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.StringUtils;

public class SignUtils {

	private static String FIELD_SIGN = "sign";
	private static String FIELD_SIGN_TYPE = "signType";

	public enum SignTypeEnum {
		MD5, HMACSHA256
	}

	/**
	 * 验证签名
	 */
	public static boolean isSignatureValid(Map<String, String> data,String key) throws Exception {
		if (!data.containsKey(FIELD_SIGN)) {
			return false;
		}
		if (!data.containsKey(FIELD_SIGN_TYPE)) {
			return false;
		}
		
		String reqSign= data.get(FIELD_SIGN);
		if(StringUtils.isEmpty(reqSign)) {
			return false;
		}
		String signTypeStr= data.get(FIELD_SIGN_TYPE);
		SignTypeEnum signType;
		if(StringUtils.isEmpty(signTypeStr)) {
			signType=SignTypeEnum.MD5;
		}else if("MD5".equals(signTypeStr)) {
			signType=SignTypeEnum.MD5;
		}else if("SHA256".equals(signTypeStr)) {
			signType=SignTypeEnum.HMACSHA256;
		}else {
			signType=SignTypeEnum.MD5;
		}
		String sign=generateSignature(data, key, signType);

		if(reqSign.equals(sign)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 生成签名
	 * @throws Exception
	 */
	public static String generateSignature(Map<String, String> data, String key, SignTypeEnum signType)throws Exception {
		Set<String> keySet = data.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		StringBuilder str = new StringBuilder();
		for (String k : keyArray) {
			if (k.equals(FIELD_SIGN)) {
				continue;
			}
			if (data.get(k).trim().length() > 0) {
				str.append(k).append("=").append(data.get(k).trim()).append("&");
			}
		}
		str.append("key=").append(key);
		if (SignTypeEnum.MD5.equals(signType)) {
			return MD5(str.toString()).toUpperCase();
		} else if (SignTypeEnum.HMACSHA256.equals(signType)) {
			return HMACSHA256(str.toString(), key);
		} else {
			throw new Exception(String.format("Invalid sign_type: %s", signType));
		}
	}

	/**
	 * 生成 MD5
	 * @param data 待处理数据
	 * @return MD5结果
	 */
	public static String MD5(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 生成 HMACSHA256
	 * @param data 待处理数据
	 * @param key  密钥
	 * @return 加密结果
	 * @throws Exception
	 */
	public static String HMACSHA256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

}
