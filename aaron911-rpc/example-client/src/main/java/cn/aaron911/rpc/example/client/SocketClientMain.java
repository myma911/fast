package cn.aaron911.rpc.example.client;

import cn.aaron911.rpc.config.RpcServiceConfig;
import cn.aaron911.rpc.proxy.RpcClientProxy;
import cn.aaron911.rpc.remoting.transport.RpcRequestTransport;
import cn.aaron911.rpc.remoting.transport.socket.SocketRpcClient;
import github.javaguide.Hello;
import github.javaguide.HelloService;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
public class SocketClientMain {
    public static void main(String[] args) {
        RpcRequestTransport rpcRequestTransport = new SocketRpcClient();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcRequestTransport, rpcServiceConfig);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
