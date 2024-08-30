package com.joyfarm.farmstival.global.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 설정 값 주입 데이터 클래스
 * - 항목이 많을땐 데이터 클래스 형태로 모아서 설정을 따로 관리하면 좋다
 */
@Data
@ConfigurationProperties(prefix="file.upload")
public class FileProperties {
    private String path; // file.upload.path
    private String url; // file.upload.url
}
