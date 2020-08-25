package cn.aaron911.common.xss;

public final class HTMLFilter {

	public static String htmlSpecialChars(String s) {
		return cn.hutool.http.HTMLFilter.htmlSpecialChars(s);
	}
}