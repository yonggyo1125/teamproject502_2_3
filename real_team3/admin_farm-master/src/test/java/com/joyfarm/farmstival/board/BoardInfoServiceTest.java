package com.joyfarm.farmstival.board;

import com.joyfarm.farmstival.board.controllers.BoardDataSearch;
import com.joyfarm.farmstival.board.entities.BoardData;
import com.joyfarm.farmstival.board.services.BoardInfoService;
import com.joyfarm.farmstival.global.ListData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardInfoServiceTest {

    @Autowired
    private BoardInfoService infoService;

    @Test
    void test1() {
        BoardDataSearch search = new BoardDataSearch();
        ListData<BoardData> data = infoService.getList(search);
        System.out.println(data);
    }
}
