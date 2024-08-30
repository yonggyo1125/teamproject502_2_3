package com.joyfarm.farmstival.board.controllers;

import com.joyfarm.farmstival.global.CommonSearch;
import lombok.Data;

import java.util.List;

/**
 * 게시글 검색을 위한 커맨드 객체
 */
@Data
public class BoardDataSearch extends CommonSearch {

    private int limit=15; //설정쪽 값에 따라 대체되야함

    private String bid; //게시판 ID
    private List<String> bids; //게시판 ID 여러개(통합검색)

    private String sort; //정렬 조건
}
