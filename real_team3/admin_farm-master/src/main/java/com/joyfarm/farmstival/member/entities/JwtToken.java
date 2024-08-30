package com.joyfarm.farmstival.member.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash(timeToLive = 100000L)
public class JwtToken implements Serializable {
    @Id
    private String sessionId; // 세션 ID

    private String token; // JWT 토큰
}
