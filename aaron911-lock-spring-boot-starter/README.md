
# Introduction

目前先只实现了基于springboot 及 redission 的 锁





# 使用方法
## 1. 在spring boot 启动类上添加注解@cn.aaron911.lock.redisson.annotation.EnableRedissonLock


## 2. 在配置文件中配置参数
```
aaron911:
    
  # redisson lock
  redisson:
    address: redis://192.168.3.193:6379
    password:
#这里如果不加redis://前缀会报URI构建错误，Caused by: java.net.URISyntaxException: Illegal character in scheme name at index 0


#其次，在redis进行连接的时候如果不对密码进行空判断，会出现AUTH校验失败的情况。Caused by: org.redisson.client.RedisException: ERR Client sent AUTH, but no password is set. channel  
#redisson.master-name=mymaster
#redisson.password=xxxx
#redisson.sentinel-addresses=10.47.91.83:26379,10.47.91.83:26380,10.47.91.83:26381      

```
