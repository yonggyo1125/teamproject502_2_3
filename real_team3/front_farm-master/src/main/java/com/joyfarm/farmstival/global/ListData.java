package com.joyfarm.farmstival.global;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListData<T> {
    private List<T> items; // 목록 데이터
    private Pagination pagination; // 페이징
    // 두개의 데이터가 항상 쌍으로 필요하기 때문에 함께 가공해서 하나의 서비스 안에 넣음
}
