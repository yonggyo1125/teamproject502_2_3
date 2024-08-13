package com.jmt.restaurant.controllers;

import com.jmt.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    /**
     * 목록 조회
     *
     * @return
     */
    @GetMapping("/list")
    public JSONData list(@RequestBody RestaurantSearch search) {

        return null;
    }

    /**
     * 상세 조회
     *
     */
    @GetMapping("/info/{rstrId}")
    public JSONData info(@PathVariable("rstrId") Long rstrId) {

        return null;
    }
}
