### 1.介绍

Spring Boot接口幂等性校验
防止接口重复调用，将幂等性参数放在请求头或者url上，对接口进行幂等性校验
后台将生成的幂等性参数存在hashMap或者redis中


### 2.使用方法
**Apache Maven**
```
<dependency>
  <groupId>cn.aaron911</groupId>
  <artifactId>aaron911-idempotent-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

- **启动类Application中添加@EnableIdempotent注解**

```
@SpringBootApplication
@EnableIdempotent
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
- **在application.yml或者application.properties中添加配置参数**

```
aaron911:
  idempotent:
    type: redis # 指定存储类型（map或者redis）,默认为map
    validTime: 300000 # 幂等性验证码保存时间, 单位：毫秒(ms)，默认为 5分钟
    idempotentPrefix: Unique_Identification_ #  幂等性验证码默认前缀
    idempotentName: unique # 放在请求头或者url参数中的幂等性校验名称
```


在WebMvcConfigurer实现类中配置
```

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.haoyongsys.erp.common.util.interceptor.IdempotentInterceptor;

/**
 * MVC配置
 * 
 *
 */
@Configuration
public class WebMvcConfig2 implements WebMvcConfigurer {
	
    
    /**
     * 幂等性
     */
    @Autowired
    private IdempotentInterceptor idempotentInterceptor;
    
 

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
       
    }
    
}

```


## 注入 cn.aaron911.idempotent.core.IdempotentCoreImpl; 使用系统默认对象， 调用 createToken()方法生成幂等性校验码，在需要携带幂等性校验码的接口携带此方法返回的数据
```
	@Autowired
	private IdempotentCoreImpl idempotentCoreImpl;
	
	String createToken = idempotentCoreImpl.createToken();
```

## 用户自定义实现 cn.aaron911.idempotent.core.IIdempotentCore 接口 并实现相关方法

- **测试**

```
@ApiIdempotent
@GetMapping("/test")
public TestBean apiIdempotent(){
    TestBean testBean = new TestBean();
    return testBean;
}
```