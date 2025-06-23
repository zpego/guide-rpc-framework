package github.javaguide.provider;

import github.javaguide.config.RpcServiceConfig;
import github.javaguide.extension.SPI;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * store and provide service object.
 *
 * @author shuang.kou
 * @createTime 2020年05月31日 16:52:00
 */
@SPI
public interface ServiceProvider {

    Map<String, Object> SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * @param rpcServiceConfig rpc service related attributes
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName rpc service name
     * @return service object
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig rpc service related attributes
     */
    void publishService(RpcServiceConfig rpcServiceConfig);

}
