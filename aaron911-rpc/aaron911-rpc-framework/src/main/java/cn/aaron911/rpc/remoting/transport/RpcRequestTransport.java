package cn.aaron911.rpc.remoting.transport;


import cn.aaron911.rpc.extension.SPI;
import cn.aaron911.rpc.remoting.dto.RpcRequest;

/**
 * send RpcRequest。
 *
 */
@SPI
public interface RpcRequestTransport {
    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
