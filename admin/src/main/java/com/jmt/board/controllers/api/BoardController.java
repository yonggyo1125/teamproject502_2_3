package com.jmt.board.controllers.api;

import com.jmt.board.entities.Board;
import com.jmt.board.services.BoardConfigInfoService;
import com.jmt.global.exceptions.RestExceptionProcessor;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("ApiBoardController")
@RequestMapping("/board/api")
public class BoardController implements RestExceptionProcessor {
    private final BoardConfigInfoService infoService;

    @GetMapping("/config/{bid}")
    public JSONData config(@PathVariable("bid") String bid) {
        Board board = infoService.get(bid);

        return new JSONData(board);
    }
}
