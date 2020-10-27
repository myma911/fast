**项目说明** 
- 采用SpringBoot、MyBatis框架，开发的一套权限系统，极低门槛，拿来即用。

 **软件需求** 
- JDK1.8
- MySQL5.5+
- Maven3.0+

<br>

 **本地部署**
- 通过git下载源码
- 创建数据库aaron911-fast，数据库编码为UTF-8
- 执行db/mysql.sql文件，初始化数据【按需导入表结构及数据】
- 修改application-dev.yml文件，更新MySQL账号和密码
- 在aaron911-api目录下，执行mvn clean install
<br>

- Eclipse、IDEA运行ApiApplication.java，则可启动项目【aaron911-api】
- aaron911-api访问路径：http://localhost:8081/swagger-ui.html

<br>
