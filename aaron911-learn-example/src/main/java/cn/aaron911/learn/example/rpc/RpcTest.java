package cn.aaron911.learn.example.rpc;

import cn.aaron911.learn.example.rpc.service.IService;
import cn.aaron911.learn.example.rpc.service.ServerServiceImpl;

/**
 * @description:
 * @author:
 * @time: 2020/11/16 10:41
 */
public class RpcTest {

    public static void main() throws Exception {
        //服务提供者只需要暴露出接口
        IService service = new ServerServiceImpl();
        RpcFramework.export(service, 2333);

        //服务调用者只需要设置依赖
        IService service2 = RpcFramework.refer(IService.class, "127.0.0.1", 2333);
        service2.hello("sss");
    }
}
