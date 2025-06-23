package github.javaguide.config;

import github.javaguide.extension.ExtensionLoader;
import github.javaguide.registry.ServiceRegistry;
import github.javaguide.utils.PropertiesUtil;
import github.javaguide.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * When the server  is closed, do something such as unregister all services
 *
 * @author shuang.kou
 * @createTime 2020年06月04日 13:11:00
 */
@Slf4j
public class CustomShutdownHook {
    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();
    private final ServiceRegistry serviceRegistry;

    public CustomShutdownHook() {
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(PropertiesUtil.getReistryType());
    }

    public static CustomShutdownHook getCustomShutdownHook() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serviceRegistry.deleteService();
            ThreadPoolFactoryUtil.shutDownAllThreadPool();
        }));
    }
}
