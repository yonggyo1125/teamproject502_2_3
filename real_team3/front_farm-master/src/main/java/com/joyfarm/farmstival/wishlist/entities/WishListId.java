package com.joyfarm.farmstival.wishlist.entities;

import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.wishlist.constants.WishType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class WishListId {
    private Long seq;
    private WishType wishType;
    private Member member;
}