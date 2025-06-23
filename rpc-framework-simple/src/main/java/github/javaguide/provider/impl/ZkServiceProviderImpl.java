package github.javaguide.provider.impl;

import github.javaguide.config.RpcServiceConfig;
import github.javaguide.enums.NamingTypeEnum;
import github.javaguide.enums.RpcErrorMessageEnum;
import github.javaguide.exception.RpcException;
import github.javaguide.extension.ExtensionLoader;
import github.javaguide.provider.ServiceProvider;
import github.javaguide.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 11:23:00
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    /**
     * key: rpc service name(interface name + version + group)
     * value: service object
     */
    private final Map<String, Object> serviceMap;

    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        serviceMap = ServiceProvider.SERVICE_MAP;
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(NamingTypeEnum.ZK.getName());
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        if (serviceMap.containsKey(rpcServiceName)) {
            return;
        }
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        try {
            this.addService(rpcServiceConfig);
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName());
        } catch (Exception e) {
            log.error("occur exception when getHostAddress", e);
        }
    }

}
