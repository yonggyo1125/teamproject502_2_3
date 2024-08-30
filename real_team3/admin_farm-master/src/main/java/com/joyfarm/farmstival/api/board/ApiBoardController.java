package com.joyfarm.farmstival.api.board;

import com.joyfarm.farmstival.board.entities.Board;
import com.joyfarm.farmstival.board.services.BoardConfigInfoService;
import com.joyfarm.farmstival.global.exceptions.RestExceptionProcessor;
import com.joyfarm.farmstival.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class ApiBoardController implements RestExceptionProcessor {

    private final BoardConfigInfoService infoService;

    /**
     * 게시판 설정
     * @param bid
     * @return
     */
    @GetMapping("/config/{bid}")
    public JSONData getBoard(@PathVariable("bid") String bid){
        Board board = infoService.get(bid);

        return new JSONData(board);
    }
}
