package cn.aaron911.limiter;

/**
 * @description 限流类型
 */
public enum LimitType {

	/**
	 * 自定义key
	 */
	CUSTOMER,

	/**
	 * 请求者IP
	 */
	IP;
}