package github.javaguide.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xiaobiaoxu
 * @Date 2023年02月24日 15:33
 */
@AllArgsConstructor
@Getter
public enum ServiceDiscoveryEnum {

    ZK("zk"),
    NACOS("nacos");

    private final String name;
}
