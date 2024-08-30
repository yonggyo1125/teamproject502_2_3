package com.joyfarm.farmstival.board.services;

import com.joyfarm.farmstival.board.controllers.RequestBoard;
import com.joyfarm.farmstival.board.entities.Board;
import com.joyfarm.farmstival.board.entities.BoardData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class BoardSaveServiceTest {

    @Autowired
    private BoardSaveService saveService;
   // @Autowired
//private BoardRepository boardRepository;

    private Board board;

    @BeforeEach
    void init(){ //게시판 등록
        board = new Board();
        board.setBid("freetalk");
        board.setBName("자유게시판");

        System.out.println(board);
      //  boardRepository.saveAndFlush(board);
    }

    @Test
    void saveTest(){
        RequestBoard form = new RequestBoard();
        form.setBid(board.getBid());
        form.setCategory("분류1");
        form.setPoster("작성자");
        form.setSubject("제목");
        form.setContent("내용");
        form.setGuestPw("1234ab");
        BoardData data = saveService.save(form);
        System.out.println(data);

        form.setMode("update");
        form.setSeq(data.getSeq());

        BoardData data2 = saveService.save(form);
        System.out.println(data2);
    }

}
