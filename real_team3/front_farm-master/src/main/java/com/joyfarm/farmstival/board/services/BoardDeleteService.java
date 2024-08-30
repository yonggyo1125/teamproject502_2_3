package com.joyfarm.farmstival.board.services;

import com.joyfarm.farmstival.board.entities.BoardData;
import com.joyfarm.farmstival.board.repositories.BoardDataRepository;
import com.joyfarm.farmstival.file.services.FileDeleteService;
import com.joyfarm.farmstival.global.constants.DeleteStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {
    private final BoardInfoService infoService;
    private final BoardDataRepository repository;
    private final FileDeleteService deleteService;

    /**
     * SOFT 삭제 - 삭제 시간 업데이트
     * - 완전 삭제 X
     * @param seq
     * @return
     */
    public BoardData delete(Long seq){
        BoardData data = infoService.get(seq); //조회할 데이터가 없으면 InfoService에서 예외 던져지게 구현 해둠

        data.setDeletedAt(LocalDateTime.now()); //삭제 시간
        repository.saveAndFlush(data);

        return data;
    }

    /**
     *  HARD 삭제 - 완전 삭제
     * @param seq
     * @return
     */
    @Transactional
    public BoardData complete(Long seq){
        BoardData data = infoService.get(seq, DeleteStatus.ALL);

        String gid = data.getGid();

        // 파일 삭제
        deleteService.delete(gid);

        //게시글 삭제
        repository.delete(data);
        repository.flush();

        return data;
    }

    /**
     * 게시글 복구기능
     * - 삭제 일시 -> null
     * @param seq
     * @return
     */
    public BoardData recover(Long seq){
        BoardData item = infoService.get(seq);
        item.setDeletedAt(null); //복구!

        repository.saveAndFlush(item);

        return item;
    }

}
