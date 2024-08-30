package com.joyfarm.farmstival.wishlist.controllers;

import com.joyfarm.farmstival.global.rests.JSONData;
import com.joyfarm.farmstival.wishlist.constants.WishType;
import com.joyfarm.farmstival.wishlist.services.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService service;

    @GetMapping("/{type}")
    public JSONData list(@PathVariable("type") String type) {
        List<Long> seqs = service.getList(WishType.valueOf(type));

        return new JSONData(seqs);
    }

    //추가
    @GetMapping("/{type}/{seq}")
    public ResponseEntity<Void> add(@PathVariable("type") String type, @PathVariable("seq") Long seq) {

        service.add(seq, WishType.valueOf(type));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //삭제
    @DeleteMapping("/{type}/{seq}")
    public ResponseEntity<Void> remove(@PathVariable("type") String type, @PathVariable("seq") Long seq) {
        service.remove(seq, WishType.valueOf(type));

        return ResponseEntity.ok().build();
    }
}