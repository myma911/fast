package cn.aaron911.idempotent.core;

import javax.servlet.http.HttpServletRequest;

import cn.aaron911.idempotent.exception.IdempotentEmptyException;

/**
 * 幂等性核心接口
 * springboot中 用户实现该接口，并注入spring中，即可实现自定义方法
 * @ClassName: IIdempotentCore
 * @author Aaron
 * @date 2020年9月1日 下午7:49:04
 */
public interface IIdempotentCore {
	
	/**
	 * 生成token方法
	 * @Title: createToken
	 * @author Aaron
	 * @Date 2020年9月1日 下午7:48:45
	 * @return String
	 */
	String createToken();
	
	
	/**
	 * 幂等性校验方法
	 * @Title: checkToken
	 * @author Aaron
	 * @Date 2020年9月1日 下午7:54:46
	 * @param request
	 * @throws IdempotentEmptyException
	 * @throws IdempotentEmptyException void
	 */
	void checkToken(HttpServletRequest request) throws IdempotentEmptyException, IdempotentEmptyException;

}
