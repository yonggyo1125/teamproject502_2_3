package com.joyfarm.farmstival.board.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joyfarm.farmstival.file.entities.FileInfo;
import com.joyfarm.farmstival.global.entities.BaseMemberEntity;
import com.joyfarm.farmstival.member.constants.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//참고: 인덱스는 특정 컬럼을 기준으로 데이터를 정렬하여 빠르게 검색할 수 있도록 도와준다!

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name="idx_board_basic", columnList = "listOrder DESC, createdAt DESC"))// 테이블의 인덱스를 정의, name: 인덱스 이름, 인덱스 정렬순서: listOrder 컬럼 기준으로 내림차순, 값이 같으면 createdAt 기준으로 내림차순(최신항목)
public class Board extends BaseMemberEntity {
    @Id
    @Column(length=30)
    private String bid; // 게시판 아이디

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    private int listOrder; // 진열 가중치 - 게시판 항목이 정렬되는 순서 결정( 큰 숫자일수록 앞에)

    @Column(length=60, nullable = false)
    private String bName; // 게시판 이름

    private boolean active; // 사용 여부

    private int rowsPerPage = 15; // 1페이지 게시글 수

    private int pageCountPc = 10; // PC 페이지 구간 갯수( 페이지네이션 표시 할 페이지 번호갯수)

    private int pageCountMobile = 5; // Mobile 페이지 구간 갯수

    private boolean useReply; // 답글 사용 여부

    private boolean useComment; // 댓글 사용 여부

    private boolean useEditor; // 에디터 사용 여부

    private boolean useUploadImage; // 이미지 첨부 사용 여부

    private boolean useUploadFile; // 파일 첨부 사용 여부

    @Column(length=10, nullable = false)
    private String locationAfterWriting = "list"; // 글 작성 후 이동 위치 (글목록 or 글 보기)

    private boolean showListBelowView; // 글 보기 하단 게시글 목록 노출 여부

    @Column(length=10, nullable = false)
    private String skin = "default"; // 스킨

    @Lob
    private String category; // 게시판 분류

    @Enumerated(EnumType.STRING) //enum 클래스 상수를 문자열로 변환해서 db에 저장
    @Column(length=20, nullable = false)
    private Authority listAccessType = Authority.ALL; // 권한 설정 - 글목록

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority viewAccessType = Authority.ALL; // 권한 설정 - 글보기

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority writeAccessType = Authority.ALL; // 권한 설정 - 글쓰기

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority replyAccessType = Authority.ALL; // 권한 설정 - 답글

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority commentAccessType = Authority.ALL; // 권한 설정 - 댓글

    @Lob
    private String htmlTop; // 게시판 상단 HTML

    @Lob
    private String htmlBottom; // 게시판 하단 HTML

    @Transient
    private List<FileInfo> htmlTopImages; // 게시판 상단 Top 이미지

    @Transient
    private List<FileInfo> htmlBottomImages; // 게시판 하단 Bottom 이미지

    /**
     * 분류 List 형태로 변환
     *
     * @return
     */
    @JsonIgnore
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();

        if (StringUtils.hasText(category)) {
            categories = Arrays.stream(category.trim().split("\\n"))
                    .map(s -> s.trim().replaceAll("\\r", ""))
                    .toList();
        }

        return categories;
    }
}
