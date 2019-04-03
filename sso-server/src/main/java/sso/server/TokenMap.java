package sso.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 功能
 *
 * @author donglight
 * @date 2019/4/2
 * @since 1.0.0
 */
public class TokenMap {

    /**
     * 泛型代表的意义 <token,<returnUrl,jsessionid>>
     */
    private static Map<String, Map<String,String>> registryAddress = new ConcurrentHashMap<>();

    public static Map<String, Map<String,String>> getTokenMap() {
        return registryAddress;
    }
}
