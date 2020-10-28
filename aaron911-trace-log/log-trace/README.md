## log-trace 提供链路日志

#### 使用注意事项

1. 引入
```xml
                    <dependency>
                       <groupId>cn.aaron911</groupId>
                       <artifactId>log-trace</artifactId>
                       <version>1.0.0</version>
                   </dependency>

```
2.1 springboot启动， 引入当前包后，启动main函数类上添加@EnableLogTrace 注解， 在项目中需要logTrace的类或者方法上添加@Trace 注解
2.2 spring 启动，引入当前包后，需要手动加入 @ComponentScan("cn.aaron911.log.trace.aspect") 把TraceAspect扫描进来否则可能不生效


3. 需要自己的项目引入aop的 （这里默认scope 为 provided）
```xml
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-aop</artifactId>
            </dependency>
```         
4. 手动打点 在需要记录的方法上加入 @Trace 就可以记录链路日志了 
```java
        @Trace
        public void testLog() {
            easyLogDubboService.testLogDubbo();
        }
```
5. 全局打点 需要自己定义切入点 (demo 如下 )  当定义全局打点时。手动打点就会失效

```java
    @Aspect
    @Component
    public class AspectConfig extends AbstractAspect {
    
        @Around("within(com.abc..*))")
        public Object around(JoinPoint joinPoint) {
            return aroundExecute(joinPoint);
        }
    }
```