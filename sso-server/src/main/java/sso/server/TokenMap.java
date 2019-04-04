package sso.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 一个大Map，保存关系对 -> (token -- [returnUrl,jsessionid])，单点注销时用
 *  这个map可以用数据库（mysql，redis）代替
 * @author donglight
 * @date 2019/4/2
 * @since 1.0.0
 */
public class TokenMap {

    /**
     * 泛型代表的意义 <token,<returnUrl,jsessionid>>
     * token是登录令牌，returnUrl是来SSO登录的系统的回调地址，jsessionid是用户与业务系统
     *
     */
    private static Map<String, Map<String,String>> registryAddress = new ConcurrentHashMap<>();

    public static Map<String, Map<String,String>> getTokenMap() {
        return registryAddress;
    }
}
