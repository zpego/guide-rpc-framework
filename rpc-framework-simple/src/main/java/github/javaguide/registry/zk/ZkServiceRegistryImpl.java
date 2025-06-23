package github.javaguide.registry.zk;

import github.javaguide.registry.ServiceRegistry;
import github.javaguide.registry.zk.util.CuratorUtils;
import github.javaguide.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * service registration  based on zookeeper
 *
 * @author shuang.kou
 * @createTime 2020年05月31日 10:56:00
 */
@Slf4j
public class ZkServiceRegistryImpl implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + getInetSocketAddress();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }

    @Override
    public void deleteService() {
        CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), getInetSocketAddress());
    }

    private InetSocketAddress getInetSocketAddress() {
        InetSocketAddress inetSocketAddress = null;
        try {
            inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), PropertiesUtil.getPort());
        } catch (Exception e) {
            log.error("get inet socket address error: ", e);
        }
        return inetSocketAddress;
    }
}
