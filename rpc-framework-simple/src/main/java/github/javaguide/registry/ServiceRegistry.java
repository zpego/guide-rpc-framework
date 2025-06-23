package github.javaguide.registry;

import github.javaguide.extension.SPI;

/**
 * service registration
 *
 * @author shuang.kou
 * @createTime 2020年05月13日 08:39:00
 */
@SPI
public interface ServiceRegistry {
    /**
     * register service
     *
     * @param rpcServiceName    rpc service name
     */
    void registerService(String rpcServiceName);

    void deleteService();
}
