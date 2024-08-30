package com.joyfarm.farmstival.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BoardViewId.class)
public class BoardView {
    @Id
    private Long seq; // 게시글 번호

    @Id
    @Column(name="_uid")
    private Integer uid; // 비회원 - guestUid(), 회원 - 회원번호
}
