package com.joyfarm.farmstival.board.controllers;

import com.joyfarm.farmstival.file.entities.FileInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RequestBoard { //커맨드객체

    private Long seq; //글 번호 - 글 수정시 필요

    private String mode = "write"; // write: 글 작성, update: 글 수정 / 작성, 수정 여부 알 수 있는 값
    private String bid; //게시판 ID
    private String gid = UUID.randomUUID().toString(); //파일, 이미지 업로드 시 필요, 중복이 되지 않는 unique 아이디로 기본값 대입
    private boolean notice; //공지글 여부 - 일반 회원 작성x 관리자만 쓰도록
    private String category; //분류
    @NotBlank
    private String poster; //작성자
    @NotBlank
    private String subject; //제목
    @NotBlank
    private String content; //내용

    private String guestPw; //비회원 비밀번호(수정, 삭제) - 선별적 필수항목

    private Long num1; //추가 필드1
    private Long num2; //추가 필드2
    private Long num3; //추가 필드3

    private String text1; //추가 필드 한줄 텍스트
    private String text2;
    private String text3;

    private String longText1; //추가 필드 여러줄
    private String longText2;

    private List<FileInfo> editorImages;
    private List<FileInfo> attachFiles;
}
