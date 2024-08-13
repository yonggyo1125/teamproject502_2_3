package com.jmt.restaurant.controllers;

import com.jmt.global.rests.JSONData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    /**
     * 목록 조회
     *
     * @return
     */
    @GetMapping("/list")
    public JSONData list() {

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
