package com.joyfarm.farmstival.member.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="jwt")
public class JwtProperties {
    private String secret; // 검증을 위한 비밀번호
    private Integer validSeconds; // 유효시간
}
