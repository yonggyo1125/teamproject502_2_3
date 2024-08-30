package com.joyfarm.farmstival.tour.controllers;

import com.joyfarm.farmstival.global.CommonSearch;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.rests.JSONData;
import com.joyfarm.farmstival.tour.entities.TourPlace;
import com.joyfarm.farmstival.tour.services.TourPlaceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour")
@RequiredArgsConstructor
public class TourPlaceController {

    private final TourPlaceInfoService infoService;

    /**
     * 목록 조회
     * @param search
     * @return
     */

    @GetMapping("/list")
    public JSONData list(@ModelAttribute TourPlaceSearch search) { // @ModelAttribute 쓰는 이유? -> 데이터가 입력되지 않는 경우의 처리를 위해(비어 있는 객체를 만들어 놓음)

        ListData<TourPlace> data = infoService.getList(search);

        return new JSONData(data);
    }

    /**
     * 상세 조회 (개별 조회)
     * @param seq
     * @return
     */

    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {
        TourPlace item = infoService.get(seq);
        return new JSONData(item);
    }

    @GetMapping("/wish")
    @PreAuthorize("isAuthenticated()")
    public JSONData wishList(@ModelAttribute CommonSearch search) {
        ListData<TourPlace> data = infoService.getWishList(search);
        return new JSONData(data);
    }
}