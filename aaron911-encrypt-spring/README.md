### 1.介绍

Spring Boot接口加密，可以对返回值、参数值通过注解的方式自动加解密

在springboot 项目中启动类上添加注解 @EnableEncryptBody

在spring 项目中 @Import({cn.aaron911.encrypt.api.EnableEncryptBodyConfig.class})

默认使用rsa非对称加密方法
可以自定义实现。。。。。。。。接口，自定义加密、解密方法，详见自定义加密解密使用说明

### 2.使用方法
**Apache Maven**
```
<dependency>
  <groupId>cn.aaron911</groupId>
  <artifactId>aaron911-encrypt-spring</artifactId>
  <version>1.0.0</version>
</dependency>
```

- **启动类Application中添加@EnableEncryptBody注解**

```
@SpringBootApplication
@EnableEncryptBody
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
- **在application.yml或者application.properties中添加RSA公钥及私钥**

```
aaron911: 
  encrypt:
    type:  aes      # type 取值为“aes”或者“des”或者“rsa” (默认aes)
    open: true 		# 是否开启加密 true  or  false （默认true）
    publicKey: 		# RSA公钥， 当type为rsa时必须配置  
    privateKey: 	# RSA私钥， 当type为rsa时必须配置
    key:			# 秘钥，当type为“aes”或者 “des”时必须配置
```
- **对返回值进行加密**

```
@Encrypt
@GetMapping("/encryption")
public TestBean encryption(){
    TestBean testBean = new TestBean();
    return testBean;
}
```
- **对传过来的加密参数解密**

```
@Decrypt
@PostMapping("/decryption")
public String Decryption(@RequestBody TestBean testBean){
    return testBean.toString();
}
```



## 





