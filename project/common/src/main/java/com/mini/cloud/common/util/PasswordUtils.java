package com.mini.cloud.common.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;

public class PasswordUtils {

	
	private static final int SALT_LENGTH = 6;
	
	/**
	 * 加密密码
	 * @param password
	 * @return
	 */
	public static String[] getMd5Password(String password) {
		String salt = RandomUtil.randomString(SALT_LENGTH);
		String md5 = getMd5Password(password, salt);
		return new String[] {md5, salt};
	}

	/**
	 * 加密密码
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String getMd5Password(String password, String salt) {
		return DigestUtil.md5Hex(DigestUtil.md5Hex(password)+salt);
	}
	
	/**
	 * 验证密码
	 * @param checkPassword
	 * @param salt
	 * @param oldMd5Password
	 * @return
	 */
	public static boolean isCorrect(String checkPassword, String salt, String oldMd5Password) {
		String checkMd5Password = getMd5Password(checkPassword, salt);
		return checkMd5Password.equals(oldMd5Password);
	}
	
}
