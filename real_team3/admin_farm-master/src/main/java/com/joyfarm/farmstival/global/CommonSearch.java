package com.joyfarm.farmstival.global;

import lombok.Data;

@Data
public class CommonSearch {
    private int page = 1;
    private int limit = 15; // 한페이지에 출력될 갯수

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드
}
