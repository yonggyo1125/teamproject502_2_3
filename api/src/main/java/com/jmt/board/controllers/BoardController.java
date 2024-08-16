package com.jmt.board.controllers;

import com.jmt.board.entities.Board;
import com.jmt.board.entities.BoardData;
import com.jmt.board.exceptions.BoardNotFoundException;
import com.jmt.board.services.BoardConfigInfoService;
import com.jmt.board.services.BoardDeleteService;
import com.jmt.board.services.BoardInfoService;
import com.jmt.board.services.BoardSaveService;
import com.jmt.board.validators.BoardValidator;
import com.jmt.global.ListData;
import com.jmt.global.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardConfigInfoService configInfoService;
    private final BoardInfoService infoService;
    private final BoardSaveService saveService;
    private final BoardDeleteService deleteService;
    private final BoardValidator validator;
    private final Utils utils;


    private Board board; // 게시판 설정
    private BoardData boardData; // 게시글 내용

    /**
     * 글 쓰기
     * @param bid
     * @return
     */
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid, Model model) {
        commonProcess(bid, "write", model);

        return utils.tpl("board/write");
    }

    // 글 수정
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "update", model);

        RequestBoard form = infoService.getForm(boardData);
        model.addAttribute("requestBoard", form);

        return utils.tpl("board/update");
    }

    // 글 작성, 수정 처리
    @PostMapping("/save")
    public String save(@Valid RequestBoard form, Errors errors, Model model) {
        String mode = form.getMode();
        mode = mode != null && StringUtils.hasText(mode.trim()) ? mode.trim() : "write";
        commonProcess(form.getBid(), mode, model);

        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("board/" + mode);
        }

        // 목록 또는 상세 보기 이동
        String url = board.getLocationAfterWriting().equals("list") ? "/board/list/" + board.getBid() : "/board/view/" + boardData.getSeq();

        return utils.redirectUrl(url);
    }

    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, @ModelAttribute BoardDataSearch search, Model model) {
        commonProcess(bid, "list", model);

        ListData<BoardData> data = infoService.getList(bid, search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("board/list");
    }

    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "view", model);

        return utils.tpl("board/view");
    }

    // 게시글 삭제
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "delete", model);

        deleteService.delete(seq);

        return utils.redirectUrl("/board/list/" + board.getBid());
    }


    /**
     * 게시판 설정이 필요한 공통 처리(모든 처리)
     *
     * @param bid : 게시판 아이디
     * @param mode
     */
    private void commonProcess(String bid, String mode) {
        board = configInfoService.get(bid).orElseThrow(BoardNotFoundException::new); // 게시판 설정

        mode = mode == null || !StringUtils.hasText(mode.trim()) ? "write" : mode.trim();

    }

    /**
     * 게시글 번호가 경로 변수로 들어오는 공통 처리
     *  게시판 설정 + 게시글 내용
     *
     * @param seq
     * @param mode
     */
    private void commonProcess(Long seq, String mode) {
        boardData = infoService.get(seq);

        commonProcess(boardData.getBoard().getBid(), mode);
    }
}
