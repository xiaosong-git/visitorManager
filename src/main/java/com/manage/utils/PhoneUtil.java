package com.manage.utils;
/**
 * 电话号码的处理工具
 * @author LZ
 *
 */
public class PhoneUtil {
	/**
	 * 将电话号码中间部分替换为****
	 * @param phone 电话号码
	 * @return
	 */
	public static String subPhone(String phone){
		if(phone == null){
			return null;
		}
		Integer len = phone.length();
		if(len < 11){
			return phone;
		}
		String result = phone.substring(0, 3) + "****" + phone.substring(len-4,len);
		return result;
	}
	
}
