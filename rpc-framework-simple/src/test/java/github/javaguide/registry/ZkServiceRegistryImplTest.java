package github.javaguide.registry;

import github.javaguide.DemoRpcService;
import github.javaguide.DemoRpcServiceImpl;
import github.javaguide.config.RpcServiceConfig;
import github.javaguide.registry.zk.ZkServiceDiscoveryImpl;
import github.javaguide.registry.zk.ZkServiceRegistryImpl;
import github.javaguide.remoting.dto.RpcRequest;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.UUID;


/**
 * @author shuang.kou
 * @createTime 2020年05月31日 16:25:00
 */
class ZkServiceRegistryImplTest {

    @Test
    void should_register_service_successful_and_lookup_service_by_service_name() {
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistryImpl();
        DemoRpcService demoRpcService = new DemoRpcServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(demoRpcService).build();
        zkServiceRegistry.registerService(rpcServiceConfig.getRpcServiceName());
        ServiceDiscovery zkServiceDiscovery = new ZkServiceDiscoveryImpl();
        RpcRequest rpcRequest = RpcRequest.builder()
//                .parameters(args)
                .interfaceName(rpcServiceConfig.getServiceName())
//                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();
        InetSocketAddress acquiredInetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
//        assertEquals(givenInetSocketAddress.toString(), acquiredInetSocketAddress.toString());
    }
}
