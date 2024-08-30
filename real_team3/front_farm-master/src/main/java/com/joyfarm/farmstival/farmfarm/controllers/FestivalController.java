package com.joyfarm.farmstival.farmfarm.controllers;

import com.joyfarm.farmstival.farmfarm.entities.Festival;
import com.joyfarm.farmstival.farmfarm.services.FestivalInfoService;
import com.joyfarm.farmstival.global.CommonSearch;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/festival") // CRUD 를 담당하는 서버로, 리액트쪽 서버와는 관련 없음!!
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalInfoService infoService;

    /* 목록 조회 */
    @GetMapping("/list")
    public JSONData list(@ModelAttribute FestivalSearch search){
        // 검색 데이터 - 쿼리스트링 뒤에 추가
        ListData<Festival> data = infoService.getList(search);

        return new JSONData(data);
    }

    /* 상세 조회 */
    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq){
        Festival data = infoService.get(seq);

        return new JSONData(data);
    }

    @GetMapping("/wish")
    @PreAuthorize("isAuthenticated()")
    public JSONData wishList(@ModelAttribute CommonSearch search) {
        ListData<Festival> data = infoService.getWishList(search);
        return new JSONData(data);
    }
}
