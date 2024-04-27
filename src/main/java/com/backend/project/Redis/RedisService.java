package com.backend.project.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static RedisTemplate<String,String> redisTemplate;

    public RedisService(RedisConfig redisConfig) {
        redisTemplate = redisConfig.redisTemplateSet();
    }

    public static void addToSet(String id){
        Long res=redisTemplate.opsForSet().add("interesting",id);
        System.out.println("trying to add" + res);
    }


    public static boolean isParentInteresting(String id){
        boolean res=redisTemplate.opsForSet().isMember("interesting",id);
        System.out.println("check karna presence"+res);
        return res;
    }
}
