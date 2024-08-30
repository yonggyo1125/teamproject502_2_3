package com.joyfarm.farmstival.board.controllers;

import com.joyfarm.farmstival.board.entities.CommentData;
import com.joyfarm.farmstival.board.services.comment.CommentDeleteService;
import com.joyfarm.farmstival.board.services.comment.CommentInfoService;
import com.joyfarm.farmstival.board.services.comment.CommentSaveService;
import com.joyfarm.farmstival.board.validators.CommentValidator;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.exceptions.BadRequestException;
import com.joyfarm.farmstival.global.rests.JSONData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentInfoService infoService;
    private final CommentSaveService saveService;
    private final CommentDeleteService deleteService;
    private final CommentValidator validator;
    private final Utils utils;

    @PostMapping
    public JSONData write(@RequestBody @Valid RequestComment form, Errors errors) {
        return save(form, errors);
    }

    @PatchMapping
    public  JSONData update(@RequestBody @Valid RequestComment form, Errors errors) {
        return save(form, errors);
    }

    public JSONData save(RequestComment form, Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }
        saveService.save(form);

        List<CommentData> items = infoService.getList(form.getBoardDataSeq());

         return new JSONData(items); // 댓글 목록으로 반환값을 내보낸다.
    }
    @GetMapping("/info/{seq}") // 댓글 하나
    public JSONData getInfo(@PathVariable("seq") Long seq) {
        CommentData item = infoService.get(seq);

        return new JSONData(item);
    }

    @GetMapping("/list/{bSeq}") // 댓글 목록
    public JSONData getList(@PathVariable("bSeq") Long bseq) {
        List<CommentData> items = infoService.getList(bseq);

        return new JSONData(items); // 댓글 목록으로 반환값을 내보낸다.
    }

    @DeleteMapping("/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
        Long bSeq = deleteService.delete(seq);

        List<CommentData> items = infoService.getList(bSeq);

        return new JSONData(items); // 댓글 목록으로 반환값을 내보낸다.

    }
}
