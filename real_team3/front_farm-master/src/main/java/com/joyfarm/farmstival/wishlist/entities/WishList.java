package com.joyfarm.farmstival.wishlist.entities;

import com.joyfarm.farmstival.global.entities.BaseEntity;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.wishlist.constants.WishType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WishListId.class)
public class WishList extends BaseEntity {

    @Id
    private Long seq;

    @Id
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private WishType wishType;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}