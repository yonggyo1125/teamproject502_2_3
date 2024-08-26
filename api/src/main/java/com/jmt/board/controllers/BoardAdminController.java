package com.jmt.board.controllers;

import com.jmt.board.entities.BoardData;
import com.jmt.board.services.BoardInfoService;
import com.jmt.global.ListData;
import com.jmt.global.constants.DeleteStatus;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/admin")
@RequiredArgsConstructor
public class BoardAdminController {

    private final BoardInfoService boardInfoService;

    @GetMapping // 목록 조회
    public JSONData getList(BoardDataSearch search) {
        ListData<BoardData> data = boardInfoService.getList(search, DeleteStatus.ALL);

        return new JSONData(data);
    }

    @PatchMapping("/{mode}") // 목록 수정, 삭제
    public JSONData updatelist(@PathVariable("mode") String mode, @RequestBody RequestAdminList form) {


        return null;
    }

    

    @GetMapping("/info/{seq}")
    public JSONData getInfo(Long seq) {
        BoardData item = boardInfoService.get(seq, DeleteStatus.ALL);

        return new JSONData(item);
    }
}
