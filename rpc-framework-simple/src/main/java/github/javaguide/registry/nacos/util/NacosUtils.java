package github.javaguide.registry.nacos.util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import github.javaguide.enums.RpcConfigEnum;
import github.javaguide.utils.PropertiesUtil;

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
        try {
            Properties nacosProperties = new Properties();

            String nacosAddress = PropertiesUtil.PROPERTIES.getOrDefault(RpcConfigEnum.NACOS_ADDRESS.getPropertyKey(), DEFAULT_NACOS_ADDRESS).toString();
            nacosProperties.put("serverAddr", nacosAddress);

            String nacosUsername = PropertiesUtil.PROPERTIES.getOrDefault(RpcConfigEnum.NACOS_USERNAME.getPropertyKey(), DEFAULT_NACOS_USERNAME).toString();
            nacosProperties.put("username", nacosUsername);

            String nacosPassword = PropertiesUtil.PROPERTIES.getOrDefault(RpcConfigEnum.NACOS_PASSWORD.getPropertyKey(), DEFAULT_NACOS_PASSWORD).toString();
            nacosProperties.put("password", nacosPassword);

            namingService = NacosFactory.createNamingService(nacosProperties);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        return namingService;
    }


}
