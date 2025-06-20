package github.javaguide.registry.nacos.util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import github.javaguide.enums.RpcConfigEnum;
import github.javaguide.utils.PropertiesFileUtil;

import java.util.Objects;
import java.util.Properties;

/**
 *
 * @Author: zhaopg
 * @Date: 2025/6/20
 */
public class NacosUtils {

    private static final String DEFAULT_NACOS_ADDRESS = "127.0.0.1:8848";
    private static final String DEFAULT_NACOS_USERNAME = "nacos";
    private static final String DEFAULT_NACOS_PASSWORD = "nacos";

    private static NamingService namingService;

    public static NamingService getNamingService() {
        if (!Objects.isNull(namingService)) {
            return namingService;
        }
        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue());

        try {
            Properties nacosProperties = new Properties();
            if (!Objects.isNull(properties)) {
                String nacosAddress = properties.getOrDefault(RpcConfigEnum.NACOS_ADDRESS.getPropertyValue(), DEFAULT_NACOS_ADDRESS).toString();
                nacosProperties.put("serverAddr", nacosAddress);

                String nacosUsername = properties.getOrDefault(RpcConfigEnum.NACOS_USERNAME.getPropertyValue(), DEFAULT_NACOS_USERNAME).toString();
                nacosProperties.put("username", nacosUsername);

                String nacosPassword = properties.getOrDefault(RpcConfigEnum.NACOS_PASSWORD.getPropertyValue(), DEFAULT_NACOS_PASSWORD).toString();
                nacosProperties.put("password", nacosPassword);

                namingService = NacosFactory.createNamingService(nacosProperties);
            }
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        return namingService;
    }


}
