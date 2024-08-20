package com.jmt.email.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value="authNum", timeToLive = 180)
public class AuthNum {
    @Id
    private String uid;

    private Integer authNum;
}
