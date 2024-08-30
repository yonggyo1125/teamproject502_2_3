package com.joyfarm.farmstival.board.services;


import com.joyfarm.farmstival.board.controllers.RequestBoardConfig;
import com.joyfarm.farmstival.board.entities.Board;
import com.joyfarm.farmstival.board.repositories.BoardRepository;
import com.joyfarm.farmstival.file.services.FileUploadDoneService;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.global.exceptions.script.AlertException;
import com.joyfarm.farmstival.member.constants.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;
    private final FileUploadDoneService fileUploadDoneService;
    private final Utils utils;

    /* 특정 게시판(폼) 등록 및 수정 */
    public void save(RequestBoardConfig form) {
        String bid = form.getBid(); //게시판 아이디
        String mode = form.getMode(); //게시판 등록 / 수정 모드 값
        mode = StringUtils.hasText(mode) ? mode : "add";

        Board board = boardRepository.findById(bid).orElseGet(Board::new); //게시판 Id 조회

        /* 게시판 작성 폼 db등록 */

        //mode가 게시판 등록일 경우 gid, bid 등록 -> 수정시에는 변경 X
        if (mode.equals("add")) {
            board.setBid(bid);
            board.setGid(form.getGid());
        }

        board.setBName(form.getBName()); //게시판 이름
        board.setActive(form.isActive()); //사용 여부
        board.setRowsPerPage(form.getRowsPerPage()); //1페이지 게시글 수
        board.setPageCountPc(form.getPageCountPc()); //PC페이지 구간 갯수
        board.setPageCountMobile(form.getPageCountMobile()); //Mobile 페이지 구간 갯수
        board.setUseReply(form.isUseReply()); //답글 사용 여부
        board.setUseComment(form.isUseComment()); //댓글 사용 여부
        board.setUseEditor(form.isUseEditor()); //에디터 사용 여부
        board.setUseUploadImage(form.isUseUploadImage()); //이미지 첨부 사용 여부
        board.setUseUploadFile(form.isUseUploadFile()); //파일 첨부 사용 여부
        board.setLocationAfterWriting(form.getLocationAfterWriting()); // 글 작성 후 이동 위치
        board.setShowListBelowView(form.isShowListBelowView()); //글 보기 하단 목록 노출 여부
        board.setSkin(form.getSkin()); //스킨
        board.setCategory(form.getCategory());//게시판 분류

        board.setListAccessType(Authority.valueOf(form.getListAccessType())); //권한 설정
        board.setViewAccessType(Authority.valueOf(form.getViewAccessType()));
        board.setWriteAccessType(Authority.valueOf(form.getWriteAccessType()));
        board.setReplyAccessType(Authority.valueOf(form.getReplyAccessType()));
        board.setCommentAccessType(Authority.valueOf(form.getCommentAccessType()));

        board.setHtmlTop(form.getHtmlTop()); //게시판 상단 HTML
        board.setHtmlBottom(form.getHtmlBottom()); //게시판 하단 HTML

        board.setListOrder(form.getListOrder()); //진열 가중치

        boardRepository.saveAndFlush(board); // db 반영

        // 파일 업로드 완료 처리
        fileUploadDoneService.process(board.getGid());
    }

    /* 게시판 목록에서 선택된 게시판 수정 */
    public void saveList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) { //선택된 체크박스가 없을때 에러처리
            throw new AlertException("수정할 게시판을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String bid = utils.getParam("bid_" + chk); //bid_0, bid_1 ...
            //input 요소의 name속성에 따른 value 속성이 반환되어 bid 값을 반환받음

            Board board = boardRepository.findById(bid).orElse(null); //게시판 id에 따른 게시판 조회
            if (board == null) continue;

            boolean active = Boolean.parseBoolean(utils.getParam("active_" + chk));
            board.setActive(active); //사용 여부 수정시

            int listOrder = Integer.parseInt(utils.getParam("listOrder_" + chk));
            board.setListOrder(listOrder); //진열 가중치 수정시
        }

        boardRepository.flush(); //저장
    }
}
