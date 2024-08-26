package com.jmt.board;

import com.jmt.board.controllers.BoardDataSearch;
import com.jmt.board.entities.BoardData;
import com.jmt.board.services.BoardInfoService;
import com.jmt.board.services.BoardUpdateService;
import com.jmt.global.ListData;
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
