package cn.aaron911.idempotent;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aaron911.idempotent.cache.Cache;
import cn.aaron911.idempotent.exception.IdempotentCreateFailedException;
import cn.aaron911.idempotent.exception.IdempotentEmptyException;
import cn.aaron911.idempotent.exception.IdempotentInvalidException;
import cn.aaron911.idempotent.property.IdempotentProperties;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IdempotentComponent {
    
    @Autowired
    private IdempotentProperties idempotentProperties;
    
    @Autowired
    private Cache cache;

    /**
     * 
     * @Title: createToken
     * @author Aaron
     * @Date 2020年8月31日 下午5:22:32
     * @return String
     * @throws IdempotentCreateFailedException 
     */
    public String createToken() {
        String str = IdUtil.objectId();
        StrBuilder token = new StrBuilder();
        token.append(idempotentProperties.getIdempotentPrefix()).append(str);
        boolean success = cache.set(token.toString(), token.toString(), idempotentProperties.getValidTime(), TimeUnit.MILLISECONDS);
        if (success) {
        	return token.toString();
        }
        // 幂等性校验码生成失败
        IdempotentCreateFailedException idempotentCreateFailedException = new IdempotentCreateFailedException();
        log.error(idempotentCreateFailedException.getCode() + ":" +idempotentCreateFailedException.getMsg());
        throw idempotentCreateFailedException;
    }

    /**
     * 
     * @Title: checkToken
     * @author Aaron
     * @Date 2020年8月31日 下午4:54:44
     * @param request void
     * 
     * @throws IdempotentEmptyException
     * @throws IdempotentInvalidException
     * 
     */
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(idempotentProperties.getIdempotentName());
        if (StrUtil.isBlank(token)) {// header中不存在token
            token = request.getParameter(idempotentProperties.getIdempotentName());
            if (StrUtil.isBlank(token)) {// parameter中也不存在token
            	IdempotentEmptyException idempotentEmptyException = new IdempotentEmptyException();
            	log.error(idempotentEmptyException.getCode() + ":" +idempotentEmptyException.getMsg());
				throw idempotentEmptyException;
            }
        }
        // 校验失败
        if (!cache.hasKey(token)) {
        	throw new IdempotentInvalidException();
        }
        // 校验失败
        Boolean del = cache.del(token);
        if (null == del || !del) {
        	IdempotentEmptyException idempotentEmptyException = new IdempotentEmptyException();
        	log.error(idempotentEmptyException.getCode() + ":" +idempotentEmptyException.getMsg());
			throw idempotentEmptyException;
        }
    }
}