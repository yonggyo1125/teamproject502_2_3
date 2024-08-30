package com.joyfarm.farmstival.board.controllers;

import lombok.Data;

import java.util.List;

@Data
public class BoardSearch { //게시판 목록을 검색하기 위한 조건을 담고있는 객체
    private int page = 1;
    private int limit = 15;

    private String bid;
    private List<String> bids;

    private String bName;
    private boolean active;

    private String sopt; // 검색 옵션 
    private String skey; // 검색 키워드
}   
