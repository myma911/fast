package cn.aaron911.api.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.aaron911.api.annotation.Login;
import cn.aaron911.api.entity.TokenEntity;
import cn.aaron911.api.service.TokenService;
import cn.aaron911.common.exception.TokenEmptyException;
import cn.aaron911.common.exception.TokenInvalidException;
import cn.hutool.core.util.StrUtil;

/**
 * 权限(Token)验证
 *
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }

        //token为空
        if(StrUtil.isBlank(token)){
            throw new TokenEmptyException();
        }

        //查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
        	throw new TokenInvalidException();
        }
        
        return true;
    }
}
