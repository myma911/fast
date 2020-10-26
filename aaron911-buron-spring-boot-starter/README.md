**项目说明** 

spring 或者 springboot 访问流量限制工具

在springboot 项目启动类中 加入 @EnableBuron 注解，并在property配置文件中设置参数

在spring 项目中 @Import({cn.aaron911.buron.EnableBuronConfiguration.class})

在拦截器中注入 BuronProcessor 实例对象 并调用对象方法

**配置参数**
aaron911.buron.

更多配置详见
cn.aaron911.buron.property.BuronProperties.java 


#### 添加依赖

```xml
<dependency>
    <groupId>cn.aaron911</groupId>
    <artifactId>aaron911-buron</artifactId>
    <version>1.0.0</version>
</dependency>
```


