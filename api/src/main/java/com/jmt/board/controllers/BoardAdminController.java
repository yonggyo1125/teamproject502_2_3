package com.jmt.board.controllers;

import com.jmt.board.entities.BoardData;
import com.jmt.board.services.BoardInfoService;
import com.jmt.board.services.admin.BoardAdminService;
import com.jmt.global.ListData;
import com.jmt.global.constants.DeleteStatus;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/admin")
@RequiredArgsConstructor
public class BoardAdminController {

    private final BoardInfoService boardInfoService;
    private final BoardAdminService boardAdminService;

    @GetMapping // 목록 조회
    public JSONData getList(BoardDataSearch search) {
        ListData<BoardData> data = boardInfoService.getList(search, DeleteStatus.ALL);

        return new JSONData(data);
    }

    @PatchMapping("/{mode}") // 목록 수정, 삭제
    public JSONData updatelist(@PathVariable("mode") String mode, @RequestBody RequestAdminList form) {
        List<BoardData> items = boardAdminService.update(mode, form.getItems());

        return new JSONData(items);
    }

    @PatchMapping("/{mode}/{seq}") // 게시글 하나 수정, 삭제
    public JSONData update(@PathVariable("mode") String mode, @PathVariable("seq") Long seq, RequestBoard form) {
        form.setSeq(seq);

        BoardData item = boardAdminService.update(mode, form);
    }

    @GetMapping("/info/{seq}")
    public JSONData getInfo(Long seq) {
        BoardData item = boardInfoService.get(seq, DeleteStatus.ALL);

        return new JSONData(item);
    }
}
