package com.joyfarm.farmstival.wishlist.services;

import com.joyfarm.farmstival.member.MemberUtil;
import com.joyfarm.farmstival.wishlist.constants.WishType;
import com.joyfarm.farmstival.wishlist.entities.QWishList;
import com.joyfarm.farmstival.wishlist.entities.WishList;
import com.joyfarm.farmstival.wishlist.entities.WishListId;
import com.joyfarm.farmstival.wishlist.repositories.WishListRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final MemberUtil memberUtil;
    private final WishListRepository repository;

    public void add(Long seq, WishType type) { // 추가
        if (!memberUtil.isLogin()) {
            return;
        }

        WishList wishList = WishList.builder()
                .wishType(type)
                .seq(seq)
                .member(memberUtil.getMember())
                .build();

        System.out.println("wishList : " + wishList);
        repository.saveAndFlush(wishList);
    }

    public void remove(Long seq, WishType type) { // 제거
        if (!memberUtil.isLogin()) {
            return;
        }

        WishListId wishListId = new WishListId(seq, type, memberUtil.getMember());
        repository.deleteById(wishListId);
        repository.flush();
    }

    public List<Long> getList(WishType type) { // 리스트
        BooleanBuilder builder = new BooleanBuilder();
        QWishList wishList = QWishList.wishList;
        builder.and(wishList.member.eq(memberUtil.getMember()))
                .and(wishList.wishType.eq(type));

        List<Long> items = ((List<WishList>)repository.findAll(builder, Sort.by(desc("createdAt")))).stream().map(WishList::getSeq).toList();

        return items;
    }
}