package com.joyfarm.farmstival.board.controllers;

import com.joyfarm.farmstival.board.entities.BoardData;
import com.joyfarm.farmstival.board.services.BoardInfoService;
import com.joyfarm.farmstival.board.services.admin.BoardAdminService;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.constants.DeleteStatus;
import com.joyfarm.farmstival.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{mode}") // 목록 수정, 삭제
    public ResponseEntity<Void> updatelist(@PathVariable("mode") String mode, @RequestBody RequestAdminList form) {
        boardAdminService.update(mode, form.getItems());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{mode}/{seq}") // 게시글 하나 수정, 삭제
    public ResponseEntity<Void> update(@PathVariable("mode") String mode, @PathVariable("seq") Long seq, @RequestBody RequestBoard form) {
        form.setSeq(seq);

        boardAdminService.update(mode, form);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/info/{seq}")
    public JSONData getInfo(Long seq) {
        BoardData item = boardInfoService.get(seq, DeleteStatus.ALL);

        return new JSONData(item);
    }

    @GetMapping("/edit/{seq}")
    public JSONData editBoard(@PathVariable("seq") Long seq) {
        RequestBoard form = boardInfoService.getForm(seq); // getForm을 사용하여 데이터 가져오기
        return new JSONData(form);
    }


}