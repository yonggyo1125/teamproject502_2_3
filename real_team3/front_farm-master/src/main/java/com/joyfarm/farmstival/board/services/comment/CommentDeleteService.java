package com.joyfarm.farmstival.board.services.comment;


import com.joyfarm.farmstival.board.entities.CommentData;
import com.joyfarm.farmstival.board.repositories.CommentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {
    private final CommentDataRepository commentDataRepository;
    private final CommentInfoService commentInfoService;

    public Long delete(Long seq) {

        CommentData data = commentInfoService.get(seq);
        Long boardDataSeq = data.getBoardData().getSeq();

        commentDataRepository.delete(data);
        commentDataRepository.flush();

        return boardDataSeq; // 게시글 번호를 가지고 와서 반환한다.
    }
}