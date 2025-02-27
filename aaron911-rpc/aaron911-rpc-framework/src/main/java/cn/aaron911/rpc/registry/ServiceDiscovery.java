package cn.aaron911.rpc.registry;

import cn.aaron911.rpc.extension.SPI;
import cn.aaron911.rpc.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * service discovery
 */
@SPI
public interface ServiceDiscovery {
    /**
     * lookup service by rpcServiceName
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
