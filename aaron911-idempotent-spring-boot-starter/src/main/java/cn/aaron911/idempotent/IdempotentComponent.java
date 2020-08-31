package cn.aaron911.idempotent;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aaron911.idempotent.exception.IdempotentCreateFailedException;
import cn.aaron911.idempotent.exception.IdempotentEmptyException;
import cn.aaron911.idempotent.exception.IdempotentInvalidException;
import cn.aaron911.idempotent.property.IdempotentProperties;
import cn.aaron911.idempotent.util.RedisUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@Component
public class IdempotentComponent {

   
    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private IdempotentProperties idempotentProperties;

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
        boolean success = redisUtil.set(token.toString(), token.toString(), idempotentProperties.getValidTime(), TimeUnit.MILLISECONDS);
        if (success) {
        	return token.toString();
        }
        // 幂等性校验码生成失败
        throw new IdempotentCreateFailedException();
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
                throw new IdempotentEmptyException();
            }
        }
        // 校验失败
        if (!redisUtil.hasKey(token)) {
        	throw new IdempotentInvalidException();
        }
        // 校验失败
        Boolean del = redisUtil.del(token);
        if (null == del || !del) {
        	throw new IdempotentInvalidException();
        }
    }
}