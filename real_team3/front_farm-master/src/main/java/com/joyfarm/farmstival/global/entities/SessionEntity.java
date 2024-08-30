package com.joyfarm.farmstival.global.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "session",timeToLive = 3600L)
public class SessionEntity {
    @Id
    private String key;

    private String value;

}
