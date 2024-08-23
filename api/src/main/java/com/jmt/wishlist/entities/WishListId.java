package com.jmt.wishlist.entities;

import com.jmt.member.entities.Member;
import com.jmt.wishlist.constants.WishType;
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
