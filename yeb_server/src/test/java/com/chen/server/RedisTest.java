package com.chen.server;

import com.alibaba.fastjson.JSON;
import com.chen.server.mapper.MenuMapper;
import com.chen.server.pojo.Admin;
import com.chen.server.pojo.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private MenuMapper mapper;

    @Test
    public void test1(){
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        List<Menu> menus = mapper.getMenusByAdminId(1);
        opsForValue.set("test", JSON.toJSONString(menus));
        String test = opsForValue.get("test");
        System.out.println(test);
    }
}
