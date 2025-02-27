package cn.aaron911.rpc.loadbalance;


import cn.aaron911.rpc.remoting.dto.RpcRequest;
import cn.aaron911.rpc.utils.CollectionUtil;

import java.util.List;

/**
 * Abstract class for a load balancing policy
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        if (CollectionUtil.isEmpty(serviceAddresses)) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest);

}
