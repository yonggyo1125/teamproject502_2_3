package com.joyfarm.farmstival.board.repositories;

import com.joyfarm.farmstival.board.entities.BoardView;
import com.joyfarm.farmstival.board.entities.BoardViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardViewRepository extends JpaRepository<BoardView, BoardViewId>, QuerydslPredicateExecutor<BoardView> {
}
