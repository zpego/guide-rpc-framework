package github.javaguide.provider.impl;

import github.javaguide.config.RpcServiceConfig;
import github.javaguide.enums.NamingTypeEnum;
import github.javaguide.enums.RpcErrorMessageEnum;
import github.javaguide.exception.RpcException;
import github.javaguide.extension.ExtensionLoader;
import github.javaguide.provider.ServiceProvider;
import github.javaguide.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 *
 * @Author: zhaopg
 * @Date: 2025/6/20
 */
@Slf4j
public class NacosServiceProviderImpl implements ServiceProvider {

    private final Map<String, Object> serviceMap;
    private final ServiceRegistry serviceRegistry;


    public NacosServiceProviderImpl() {
        serviceMap = ServiceProvider.SERVICE_MAP;
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(NamingTypeEnum.NACOS.getName());
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
