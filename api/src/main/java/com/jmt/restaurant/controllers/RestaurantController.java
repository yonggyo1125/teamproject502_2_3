package com.jmt.restaurant.controllers;

import com.jmt.global.ListData;
import com.jmt.global.rests.JSONData;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantInfoService infoService;

    /**
     * 목록 조회
     *
     * @return
     */
    @GetMapping("/list")
    public JSONData list(@ModelAttribute RestaurantSearch search) {

        ListData<Restaurant> data = infoService.getList(search);

        return new JSONData(data);
    }

    /**
     * 상세 조회
     *
     */
    @GetMapping("/info/{rstrId}")
    public JSONData info(@PathVariable("rstrId") Long rstrId) {

        Restaurant data = infoService.get(rstrId);

        return new JSONData(data);
    }
}
