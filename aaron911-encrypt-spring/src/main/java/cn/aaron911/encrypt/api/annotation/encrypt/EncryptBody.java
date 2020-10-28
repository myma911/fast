package cn.aaron911.encrypt.api.annotation.encrypt;



import java.lang.annotation.*;

import cn.aaron911.encrypt.api.enums.EncryptBodyMethod;
import cn.aaron911.encrypt.api.enums.SHAEncryptType;

/**
 * <p>加密{@link org.springframework.web.bind.annotation.ResponseBody}响应数据，可用于整个控制类或者某个控制器上</p>
 * 
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptBody {

    EncryptBodyMethod value() default EncryptBodyMethod.MD5;

    String otherKey() default "";

    SHAEncryptType shaType() default SHAEncryptType.SHA256;

}
