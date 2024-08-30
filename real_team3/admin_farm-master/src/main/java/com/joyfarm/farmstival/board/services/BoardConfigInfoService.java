package com.joyfarm.farmstival.board.services;


import com.joyfarm.farmstival.board.controllers.BoardSearch;
import com.joyfarm.farmstival.board.controllers.RequestBoardConfig;
import com.joyfarm.farmstival.board.entities.Board;
import com.joyfarm.farmstival.board.entities.QBoard;
import com.joyfarm.farmstival.board.exceptions.BoardNotFoundException;
import com.joyfarm.farmstival.board.repositories.BoardRepository;
import com.joyfarm.farmstival.file.entities.FileInfo;
import com.joyfarm.farmstival.file.services.FileInfoService;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Pagination;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {
    private final BoardRepository boardRepository;
    private final FileInfoService fileInfoService;
    private final HttpServletRequest request;

    /**
     * 게시판 설정 조회
     *
     * @param bid
     * @return
     */
    public Board get(String bid) {
        Board board = boardRepository.findById(bid).orElseThrow(BoardNotFoundException::new);

        addBoardInfo(board);

        return board;

    }

    public RequestBoardConfig getForm(String bid) {
        Board board = get(bid);

        RequestBoardConfig form = new ModelMapper().map(board, RequestBoardConfig.class);
        form.setListAccessType(board.getListAccessType().name());
        form.setViewAccessType(board.getViewAccessType().name());
        form.setWriteAccessType(board.getWriteAccessType().name());
        form.setReplyAccessType(board.getReplyAccessType().name());
        form.setCommentAccessType(board.getCommentAccessType().name());

        form.setMode("edit");

        return form;
    }

    /**
     * 게시판 설정 추가 정보
     *      - 에디터 첨부 파일 목록
     * @param board
     */
    public void addBoardInfo(Board board) {
        String gid = board.getGid();

        List<FileInfo> htmlTopImages = fileInfoService.getList(gid, "html_top");

        List<FileInfo> htmlBottomImages = fileInfoService.getList(gid, "html_bottom");

        board.setHtmlTopImages(htmlTopImages);
        board.setHtmlBottomImages(htmlBottomImages);
    }

    /**
     * 게시판 설정 목록
     *
     * @param search
     * @return
     */
    public ListData<Board> getList(BoardSearch search, boolean isAll) {
        int page = Math.max(search.getPage(), 1); // 페이지 번호(1 이하일 경우 1로 설정)
        int limit = search.getLimit(); //페이지 당 항목 수
        limit = limit < 1 ? 10 : limit; //항목 수가 1 미만일경우 기본값 10으로 설정, 한페이지에 10개 항목
        QBoard board = QBoard.board;
        BooleanBuilder andBuilder = new BooleanBuilder(); //andBuilder -> 데이터베이스 쿼리의 WHERE절에 AND 연산자

        /* 검색 조건 처리 S */
        String bid = search.getBid();
        List<String> bids = search.getBids();
        String bName = search.getBName();

        String sopt = search.getSopt(); // 검색 옵션 (드롭바)
        sopt = StringUtils.hasText(sopt) ? sopt.trim() : "ALL"; //검색 옵션 기본값 통합검색
        String skey = search.getSkey(); // 검색 키워드(직접 입력한)

        if (StringUtils.hasText(bid)) { // 게시판 ID
            andBuilder.and(board.bid.contains(bid.trim())); //게시판 id 와 일치하는 게시판 단일 검색
        }

        // 게시판 ID 여러개 조회
        if (bids != null && !bids.isEmpty()) { //bids 리스트가 비어있지 않은 경우
            andBuilder.and(board.bid.in(bids)); //주어진 여러 게시판 id 중 하나라도 일치하면 해당 게시판을 검색 결과로 포함시킴
        }

        // isAll이 false인 경우 노출 상태인 게시판만 조회, true인 경우 모든 게시판 조회
        if (!isAll) {
            andBuilder.and(board.active.eq(true)); //board active 필드가 true인 게시판만 검색 결과에 포함시킴
        }

        if (StringUtils.hasText(bName)) { // 게시판 명으로 검색
            andBuilder.and(board.bName.contains(bName.trim())); //bName 필드가 해당 이름을 포함한 게시판 검색
        }


        // 조건별 키워드 검색
        if (StringUtils.hasText(skey)) { //검색 키워드가 있는지 확인
            skey = skey.trim();

            //검색 키워드를 포함하는지 검사
            BooleanExpression cond1 = board.bid.contains(skey);
            BooleanExpression cond2 = board.bName.contains(skey);

            if (sopt.equals("bid")) { //검색 옵션이 게시판 id 일떄
                andBuilder.and(cond1); //andBuilder에 cond1조건 추가
            } else if (sopt.equals("bName")) { //검색 옵션이 게시판명 일때
                andBuilder.and(cond2); //andBuilder에 cond2조건 추가
            } else { // 통합검색 : bid + bName
                //두 조건을 모두 검색 할 수 있도록 OR로 결합
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(cond1)
                        .or(cond2); //두조건중 하나라도 만족하면 결과에 포함시킨다.
                andBuilder.and(orBuilder);
            }
        }

        /* 검색 조건 처리 E */

        //조회 한 게시판 목록을 페이지별로 나누어 보여주는 작업
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt"))); //페이지 번호, 한 페이지에 보여줄 항목 수, 정렬 기준
        Page<Board> data = boardRepository.findAll(andBuilder, pageable); //andBuilder와 pageable을 사용해 게시판 목록을 db에서 가져옴

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), limit, 10, request);

        return new ListData<>(data.getContent(), pagination);
        //data.getContent: 실제로 페이지별로 나누어진 게시판 항목, 페이지네이션 정보와 하나의 객채로 만들어 반환
    }

    /**
     * 노출 상태인 게시판 목록
     *
     * @param search
     * @return
     */
    public ListData<Board> getList(BoardSearch search) {
        return getList(search, false);
    } //노출 상태의 상관없이 모든 게시판 검색

    /**
     * 노출 가능한 모든 게시판 목록
     *
     * @return
     */
    public List<Board> getList() {
        QBoard board = QBoard.board;

        List<Board> items = (List<Board>)boardRepository.findAll(board.active.eq(true), Sort.by(desc("listOrder"), desc("createdAt")));
        // 노출 상태가 true인 게시판만 검색

        return items;
    }
}
