package cn.aaron911.encrypt.api.annotation.decrypt;



import java.lang.annotation.*;

import cn.aaron911.encrypt.api.enums.DecryptBodyMethod;

/**
 * <p>解密含有{@link org.springframework.web.bind.annotation.RequestBody}注解的参数请求数据，可用于整个控制类或者某个控制器上</p>
 * 
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecryptBody {

    DecryptBodyMethod value() default DecryptBodyMethod.AES;

    String otherKey() default "";

}
