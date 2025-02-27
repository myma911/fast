package cn.aaron911.rpc.example.server;

import cn.aaron911.rpc.annotation.RpcScan;
import cn.aaron911.rpc.config.RpcServiceConfig;
import cn.aaron911.rpc.example.server.service.HelloServiceImpl2;
import cn.aaron911.rpc.example.service.api.HelloService;

import cn.aaron911.rpc.remoting.transport.netty.server.NettyRpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

/**
 * Server: Automatic registration service via @RpcService annotation
 */
@RpcScan(basePackage = {"cn.aaron911.rpc.example.server.service"})
public class NettyServerMain {
    public static void main(String[] args) {
        // Register service via annotation
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");
        // Register service manually
        HelloService helloService2 = new HelloServiceImpl2();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(helloService2).build();
        nettyRpcServer.registerService(rpcServiceConfig);
        new Scanner(System.in).nextLine();
    }
}
