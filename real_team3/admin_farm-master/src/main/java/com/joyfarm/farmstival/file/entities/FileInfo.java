package com.joyfarm.farmstival.file.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joyfarm.farmstival.global.entities.BaseMemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfo extends BaseMemberEntity {

    @Id
    @GeneratedValue
    private Long seq; // 서버에 업로드될 파일 이름  - seq.확장자

    @Column(length=45, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @Column(length=45)
    private String location; // 그룹 안에 세부 위치

    @Column(length=80, nullable = false)
    private String fileName;

    @Column(length=30)
    private String extension; // 파일 확장자

    @Column(length=80)
    private String contentType;

    private boolean done; // 그룹 작업 완료 여부

    @Transient
    private String fileUrl; // 파일 접근 URL

    @Transient
    private String filePath; // 파일 업로드 경로
}
