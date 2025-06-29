package github.javaguide.utils;

import github.javaguide.enums.NamingTypeEnum;
import github.javaguide.enums.RpcConfigEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author shuang.kou
 * @createTime 2020年07月21日 14:25:00
 **/
@Slf4j
public final class PropertiesUtil {

    private static final String DEFAULT_PORT = "9198";

    public static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    /**
     * 读取配置文件
     */
    public static void readPropertiesFile() {
        String fileName = RpcConfigEnum.RPC_CONFIG_PATH.getPropertyKey();
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath = "";
        if (url != null) {
            try {
                // 使用 toURI() 避免 getPath() 导致的非法字符问题
                rpcConfigPath = new File(url.toURI()).getPath() + File.separator + fileName;
            } catch (URISyntaxException e) {
                log.error("Invalid URL or URI syntax when reading config file", e);
                return;
            }
        }

        try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get(rpcConfigPath)), StandardCharsets.UTF_8)) {
            PROPERTIES.load(inputStreamReader);
        } catch (IOException e) {
            log.error("occur exception when read properties file [{}]", fileName);
        }
    }

    /**
     * 获取注册中心类型
     *
     * @return 注册中心类型
     */
    public static String getReistryType() {
        String propertyKey = RpcConfigEnum.RPC_NANING_TYPE.getPropertyKey();
        return PROPERTIES.getProperty(propertyKey, NamingTypeEnum.NACOS.getName());
    }

    /**
     * 获取服务IP
     * @return
     * @throws Exception
     */
    public static String getIp()  {
        String propertyKey = RpcConfigEnum.RPC_SERVICE_IP.getPropertyKey();
        String DEFAULT_IP = null;
        try {
            DEFAULT_IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return PROPERTIES.getProperty(propertyKey, DEFAULT_IP);
    }

    /**
     * 获取服务端口
     *
     * @return 服务端口
     */
    public static int getPort() {
        String propertyKey = RpcConfigEnum.RPC_SERVICE_IP.getPropertyKey();
        String port = PROPERTIES.getProperty(propertyKey, DEFAULT_PORT);
        return Integer.parseInt(port);
    }

}
