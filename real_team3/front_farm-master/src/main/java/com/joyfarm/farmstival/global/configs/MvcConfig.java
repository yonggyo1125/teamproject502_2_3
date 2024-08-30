package com.joyfarm.farmstival.global.configs;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
@EnableDiscoveryClient // 실제 서비스 정보의 URL을 알 수 있다.
public class MvcConfig implements WebMvcConfigurer {

}
