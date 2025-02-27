package cn.aaron911.rpc.example.server;

import cn.aaron911.rpc.config.RpcServiceConfig;
import cn.aaron911.rpc.example.server.service.HelloServiceImpl;
import cn.aaron911.rpc.example.service.api.HelloService;
import cn.aaron911.rpc.remoting.transport.socket.SocketRpcServer;


public class SocketServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        rpcServiceConfig.setService(helloService);
        socketRpcServer.registerService(rpcServiceConfig);
        socketRpcServer.start();
    }
}
