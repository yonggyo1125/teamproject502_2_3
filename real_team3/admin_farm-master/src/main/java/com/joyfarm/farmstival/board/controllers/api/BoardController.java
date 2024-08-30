package com.joyfarm.farmstival.board.controllers.api;


import com.joyfarm.farmstival.board.entities.Board;
import com.joyfarm.farmstival.board.services.BoardConfigInfoService;
import com.joyfarm.farmstival.global.exceptions.RestExceptionProcessor;
import com.joyfarm.farmstival.global.rests.JSONData;
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
