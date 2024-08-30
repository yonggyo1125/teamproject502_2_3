package com.joyfarm.farmstival.board.services;

import com.joyfarm.farmstival.board.entities.BoardData;
import com.joyfarm.farmstival.board.entities.BoardView;
import com.joyfarm.farmstival.board.entities.QBoardView;
import com.joyfarm.farmstival.board.repositories.BoardDataRepository;
import com.joyfarm.farmstival.board.repositories.BoardViewRepository;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 조회수
 * */
@Service
@RequiredArgsConstructor
public class BoardViewCountService {
    private final BoardViewRepository viewRepository;
    private final BoardDataRepository dataRepository;
    private final MemberUtil memberUtil;
    private final Utils utils;

    public void update(Long seq) {
        BoardData data = dataRepository.findById(seq).orElse(null);
        if (data == null) {
            return;
        }

        int uid = memberUtil.isLogin() ? memberUtil.getMember().getSeq().intValue() : utils.guestUid();

        BoardView boardView = new BoardView(seq, uid);
        viewRepository.saveAndFlush(boardView);

        // 전체 조회수
        QBoardView bv = QBoardView.boardView;
        long total = viewRepository.count(bv.seq.eq(seq));

        data.setViewCount((int)total);
        dataRepository.saveAndFlush(data);
    }
}
