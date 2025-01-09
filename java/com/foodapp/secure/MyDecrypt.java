package com.foodapp.secure;

public class MyDecrypt {
	public static String decrypt(String str) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			sb.append((char) (str.charAt(i) - 2));
		}
		return sb.toString();
	}
}
