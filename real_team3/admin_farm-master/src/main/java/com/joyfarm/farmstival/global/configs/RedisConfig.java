package com.joyfarm.farmstival.global.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis: Key, Value 구조의 비정형 데이터를 저장하고 관리하기 위한 오픈 소스 기반의 비관계형 데이터 베이스 관리 시스템 (DBMS)
 */
@Configuration
public class RedisConfig { //Redis 데이터베이스와 연결 설정

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);//Redis와 연결 제공하는 클래스
    }

    /* redisTemplate: Redis의 다양한 데이터 구조에 대한 메서드 제공 */
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true); //Redis의 트랜잭션 활성화

        redisTemplate.setKeySerializer(new StringRedisSerializer()); //Redis에서 키를 문자열로 직렬화(문자열을 Redis에 저장) 하고 역직렬화(Redis에서 가져온 문자열을 자바 객체 문자열로 변환) 한다.
        redisTemplate.setValueSerializer(new StringRedisSerializer()); //Redis 값을 문자열로 직렬화하고 역직렬화 한다.

        redisTemplate.setHashKeySerializer(new StringRedisSerializer()); // 해시의 키를 직렬화
        redisTemplate.setHashValueSerializer(new StringRedisSerializer()); // 해시의 값을 직렬화

        return redisTemplate;
    }
}
