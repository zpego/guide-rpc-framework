package github.javaguide.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import github.javaguide.registry.ServiceRegistry;
import github.javaguide.registry.nacos.util.NacosUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


/**
 *
 * @Author: zhaopg
 * @Date: 2025/6/20
 */
@Slf4j
public class NacosServiceRegistryImpl implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        try {
            NamingService namingService = NacosUtils.getNamingService();
            namingService.registerInstance(rpcServiceName,inetSocketAddress.getHostString(),inetSocketAddress.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}
