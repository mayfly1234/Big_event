package com.luo.big_event.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    // JWT签名密钥
    private static final String KEY = "luojiagan666";

    /**
     * 接收业务数据，生成token并返回
     * @param claims 业务载荷数据
     * @return JWT字符串
     */
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                // 设置有效期：12小时
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256(KEY));
    }

    /**
     * 接收token，验证token，返回业务载荷数据
     * @param token JWT令牌
     * @return 存储的业务数据Map
     */
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}