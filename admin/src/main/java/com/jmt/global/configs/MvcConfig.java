package com.jmt.global.configs;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableDiscoveryClient
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    /*
     * <input type="hidden" name="_method" value="PATCH">->patch 방식으로 요청
     * ?_method=DELETE
     * */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {//_method 방식으로 보냈을 때 해당 방식으로 처리
        return new HiddenHttpMethodFilter();
    }
}