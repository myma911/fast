package cn.aaron911.api.xxs;

public final class HTMLFilter {

	public static String htmlSpecialChars(String s) {
		return cn.hutool.http.HTMLFilter.htmlSpecialChars(s);
	}
}