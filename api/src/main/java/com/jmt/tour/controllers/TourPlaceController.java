package com.jmt.tour.controllers;

import com.jmt.global.CommonSearch;
import com.jmt.global.ListData;
import com.jmt.global.rests.JSONData;
import com.jmt.tour.entities.TourPlace;
import com.jmt.tour.services.TourPlaceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour")
@RequiredArgsConstructor
public class TourPlaceController {

    private final TourPlaceInfoService infoService;

    @GetMapping("/list")
    public JSONData list(@ModelAttribute TourPlaceSearch search) {

        ListData<TourPlace> data = infoService.getList(search);

        return new JSONData(data);
    }

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
