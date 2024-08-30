package com.joyfarm.farmstival.board;

import com.joyfarm.farmstival.board.controllers.BoardDataSearch;
import com.joyfarm.farmstival.board.entities.BoardData;
import com.joyfarm.farmstival.board.services.BoardInfoService;
import com.joyfarm.farmstival.board.services.BoardUpdateService;
import com.joyfarm.farmstival.global.ListData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class BoardUpdateServiceTest {

    @Autowired
    private BoardUpdateService updateService;

    @Autowired
    private BoardInfoService infoService;

    @Test
    void test1() {
        ListData<BoardData> data = infoService.getList(new BoardDataSearch());

        List<BoardData> items = data.getItems().stream()
                .map(item -> {
                    item.setSubject("(수정)" + item.getSubject());
                    return item;
                }).toList();

        updateService.update("update", data.getItems());
    }
}
