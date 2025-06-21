package github.javaguide.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    RPC_NANING_TYPE("rpc.registry.type"),
    ZK_ADDRESS("rpc.zookeeper.address"),
    NACOS_ADDRESS("rpc.nacos.address"),
    NACOS_USERNAME("rpc.nacos.username"),
    NACOS_PASSWORD("rpc.nacos.password");

    private final String propertyKey;

}
