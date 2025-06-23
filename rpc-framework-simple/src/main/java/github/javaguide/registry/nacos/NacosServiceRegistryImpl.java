package github.javaguide.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import github.javaguide.provider.ServiceProvider;
import github.javaguide.registry.ServiceRegistry;
import github.javaguide.registry.nacos.util.NacosUtils;
import github.javaguide.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 *
 * @Author: zhaopg
 * @Date: 2025/6/20
 */
@Slf4j
public class NacosServiceRegistryImpl implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName) {
        try {
            NamingService namingService = NacosUtils.getNamingService();
            namingService.registerInstance(rpcServiceName, PropertiesUtil.getIp(), PropertiesUtil.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteService() {
        Map<String, Object> serviceMap = ServiceProvider.SERVICE_MAP;
        try {
            NamingService namingService = NacosUtils.getNamingService();
            for (String serviceName : serviceMap.keySet()) {
                log.info("delete service: {}", serviceName);
                namingService.deregisterInstance(serviceName, PropertiesUtil.getIp(), PropertiesUtil.getPort());
            }
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

}
