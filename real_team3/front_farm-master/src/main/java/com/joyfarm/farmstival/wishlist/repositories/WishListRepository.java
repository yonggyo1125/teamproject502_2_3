package com.joyfarm.farmstival.wishlist.repositories;

import com.joyfarm.farmstival.wishlist.entities.WishList;
import com.joyfarm.farmstival.wishlist.entities.WishListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface WishListRepository extends JpaRepository<WishList, WishListId>, QuerydslPredicateExecutor<WishList> {
}