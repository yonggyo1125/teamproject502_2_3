package com.jmt.board.services;

import com.jmt.board.entities.BoardData;
import com.jmt.board.entities.BoardView;
import com.jmt.board.entities.QBoardView;
import com.jmt.board.repositories.BoardDataRepository;
import com.jmt.board.repositories.BoardViewRepository;
import com.jmt.global.Utils;
import com.jmt.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
