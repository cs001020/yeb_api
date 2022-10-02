package com.chen.server.config.redis;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 *
 * @author CHEN
 * @date 2022/10/02
 */
@Configurable
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        //设置String类型key序列化气
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置String类型value序列化气
        redisTemplate.setValueSerializer(serializer);
        //设置Hash类型key序列化气
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //设置Hash类型Value序列化气
        redisTemplate.setHashValueSerializer(serializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
