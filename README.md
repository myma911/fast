**项目说明** 
- 基于spring 或者springboot 的各种开发工具包，快速集成各种实用功能
<br>




## 包含组件
 | 模块      			           |     介绍                                                                          													 |
| -------------------|---------------------------------------------------------------------------------- |
| aaron911-admin     |     后台权限管理系统                                              |
| aaron911-api       |     快速实现后台接口功能                  |
| aaron911-buron-spring       |     限流过滤器实现                                                                     |
| aaron911-dynamic-datasource-spring-boot-starter       |     基于springboot 的动态数据源                                               |
| aaron911-encrypt-spring-boot-starter       |     Spring Boot接口加密，可以对返回值、参数值通过注解的方式自动加解密                                          |
| aaron911-esclientrhl-spring-boot-starter      |     基于注解及springboot的elasticsearch 快速集成                                          |
| aaron911-file-spring          |     基于spring的文件上传集成                                     |
| aaron911-generator         |     代码生成器                                           |
| aaron911-idempotent-spring-boot-starter       |     接口幂等性校验            |
| aaron911-limiter-spring-boot-starter        |     springboot + aop + Lua分布式限流的最佳实践                                            |
| aaron911-lock-spring-boot-starter         |     实现了基于springboot 及 redission 的 锁                                                        |
| aaron911-minio-spring-boot-starter      |     spring boot 集成minio文件上传                                                     |
| aaron911-oauth     |     第三方授权登录                                        |
| aaron911-pay      |     支付集成                                                      |
| aaron911-spider       |     爬虫集成                                                                 |
| aaron911-sso     |     单点登录实现                                                                   |
| aaron911-trace-log         |     日志收集及展示集成                                                       |

可以根据需求对每个模块单独引入，也可以通过引入`aaron911-fast`方式引入所有模块。

## 安装

### Maven
在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
    <groupId>cn.aaron911</groupId>
	<artifactId>aaron911-fast</artifactId>
	<version>1.0.0</version>
</dependency>
```
