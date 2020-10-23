log一个简单易用的java分布式日志组件
### 一.系统介绍

 1. 无入侵的分布式日志系统，基于log4j、log4j2、logback搜集日志，设置链路ID，方便查询关联日志
 
 2. 基于elasticsearch作为查询引擎
 
 3. 高吞吐，查询效率高
 
 4. 全程不占应用程序本地磁盘空间，免维护;对于项目透明，不影响项目本身运行
 

 
### 二.架构

 
* log-core 核心组件包含日志搜集端，负责搜集日志并推送到kafka，redis等队列

* log-server 负责把队列中的日志日志异步写入到elasticsearch 

* log-ui 前端展示，日志查询界面

* log-demo 基于springboot的使用案例

   
### 三.使用方法

  
  ### 前提:kafka或者redis  和 elasticsearch（版本6.8以上最好） 自行安装完毕，版本兼容已经做了，理论不用考虑ES版本
  