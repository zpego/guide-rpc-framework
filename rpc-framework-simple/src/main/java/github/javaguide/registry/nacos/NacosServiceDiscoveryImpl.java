package github.javaguide.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import github.javaguide.registry.ServiceDiscovery;
import github.javaguide.registry.nacos.util.NacosUtils;
import github.javaguide.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 *
 * @Author: zhaopg
 * @Date: 2025/6/20
 */
@Slf4j
public class NacosServiceDiscoveryImpl implements ServiceDiscovery {

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        InetSocketAddress inetSocketAddress = null;
        try {
            NamingService namingService = NacosUtils.getNamingService();
            Instance instance = namingService.selectOneHealthyInstance(rpcRequest.getRpcServiceName());
            inetSocketAddress = new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        return inetSocketAddress;
    }
}
