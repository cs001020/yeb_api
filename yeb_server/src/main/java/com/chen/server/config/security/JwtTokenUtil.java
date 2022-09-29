package com.chen.server.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken工具类
 *
 * @author CHEN
 * @since  2022/09/29
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成JWT令牌
     *
     * @param userDetails 用户详细信息
     * @return {@link String}
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中得到用户名
     *
     * @param token 令牌
     * @return {@link String}
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证Token是否有效
     *
     * @param token       令牌
     * @param userDetails 用户详细信息
     * @return boolean
     */
    public boolean validateToken(String token,UserDetails userDetails){
        String userNameFromToken = getUserNameFromToken(token);
        return userNameFromToken.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }

    /**
     * 刷新Token
     *
     * @param token 令牌
     * @return {@link String}
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 判断Token时候可以刷新
     *
     * @param token 令牌
     * @return boolean
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 判断Token时候失效
     *
     * @param token 令牌
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        Date expireDate=getExpiredDataFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token 令牌
     * @return {@link Date}
     */
    private Date getExpiredDataFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从Token中获取荷载
     *
     * @param token 令牌
     * @return {@link Claims}
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims=null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据荷载生成JWT令牌
     *
     * @param claims 索赔
     * @return {@link String}
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token失效事件
     *
     * @return {@link Date}
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
