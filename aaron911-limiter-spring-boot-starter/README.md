**项目说明** 
- springboot + aop + Lua分布式限流的最佳实践
<br>


** 限流方案
*** 漏桶算法
漏桶算法思路很简单，我们把水比作是请求，漏桶比作是系统处理能力极限，水先进入到漏桶里，漏桶里的水按一定速率流出，当流出的速率小于流入的速率时，
由于漏桶容量有限，后续进入的水直接溢出（拒绝请求），以此实现限流。


*** 令牌桶算法
令牌桶算法的原理也比较简单，系统会维护一个令牌（token）桶，以一个恒定的速度往桶里放入令牌（token），这时如果有请求进来想要被处理，
则需要先从桶里获取一个令牌（token），当桶里没有令牌（token）可取时，则该请求将被拒绝服务。令牌桶算法通过控制桶的容量、发放令牌的速率，来达到对请求的限制。


*** Redis + Lua
很多同学不知道Lua是啥？个人理解，Lua脚本和 MySQL数据库的存储过程比较相似，他们执行一组命令，所有命令的执行要么全部成功或者失败，以此达到原子性。也可以把Lua脚本理解为，一段具有业务逻辑的代码块。

而Lua本身就是一种编程语言，虽然redis 官方没有直接提供限流相应的API，但却支持了 Lua 脚本的功能，可以使用它实现复杂的令牌桶或漏桶算法，也是分布式系统中实现限流的主要方式之一。

相比Redis事务，Lua脚本的优点：

减少网络开销： 使用Lua脚本，无需向Redis 发送多次请求，执行一次即可，减少网络传输
原子操作：Redis 将整个Lua脚本作为一个命令执行，原子，无需担心并发
复用：Lua脚本一旦执行，会永久保存 Redis 中,，其他客户端可复用
Lua脚本大致逻辑如下：

```
-- 获取调用脚本时传入的第一个key值（用作限流的 key）
local key = KEYS[1]
-- 获取调用脚本时传入的第一个参数值（限流大小）
local limit = tonumber(ARGV[1])

-- 获取当前流量大小
local curentLimit = tonumber(redis.call('get', key) or "0")

-- 是否超出限流
if curentLimit + 1 > limit then
    -- 返回(拒绝)
    return 0
else
    -- 没有超出 value + 1
    redis.call("INCRBY", key, 1)
    -- 设置过期时间
    redis.call("EXPIRE", key, 2)
    -- 返回(放行)
    return 1
end
```

- 通过KEYS[1] 获取传入的key参数
- 通过ARGV[1]获取传入的limit参数
- redis.call方法，从缓存中get和key相关的值，如果为null那么就返回0
- 接着判断缓存中记录的数值是否会大于限制大小，如果超出表示该被限流，返回0
- 如果未超过，那么该key的缓存值+1，并设置过期时间为1秒钟以后，并返回缓存值+1




## 使用

### 在 application.properties 文件中配置提前搭建好的 redis 服务地址和端口。
```
spring.redis.host=127.0.0.1

spring.redis.port=6379
```

### 在springboot application 类上启用注解 @EnableAaron911Limiter



### 控制层实现
将@Limit注解作用在需要进行限流的接口方法上，下边我们给方法设置@Limit注解，在10秒内只允许放行3个请求，这里为直观一点用AtomicInteger计数。

```

@RestController
public class LimiterController {

    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_2 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_3 = new AtomicInteger();


    @Limit(key = "limitTest", period = 10, count = 3)
    @GetMapping("/limitTest1")
    public int testLimiter1() {

        return ATOMIC_INTEGER_1.incrementAndGet();
    }


    @Limit(key = "customer_limit_test", period = 10, count = 3, limitType = LimitType.CUSTOMER)
    @GetMapping("/limitTest2")
    public int testLimiter2() {

        return ATOMIC_INTEGER_2.incrementAndGet();
    }


    @Limit(key = "ip_limit_test", period = 10, count = 3, limitType = LimitType.IP)
    @GetMapping("/limitTest3")
    public int testLimiter3() {

        return ATOMIC_INTEGER_3.incrementAndGet();
    }

}
```




