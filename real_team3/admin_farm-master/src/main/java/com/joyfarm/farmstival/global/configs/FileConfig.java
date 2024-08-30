package com.joyfarm.farmstival.global.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)//FileProperties 에 있는 설정
public class FileConfig implements WebMvcConfigurer {

   private final FileProperties properties;

   /* 특정 요청에 대한 정적 리소스 처리 핸들러 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("properties:" + properties);
        registry.addResourceHandler(properties.getUrl() + "**") //upload/**
                .addResourceLocations("file:///" + properties.getPath() + "/"); // file.path 환경변수로 지정

    }
}
