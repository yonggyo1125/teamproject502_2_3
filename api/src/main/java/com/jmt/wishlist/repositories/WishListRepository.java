package com.jmt.wishlist.repositories;

import com.jmt.wishlist.entities.WishList;
import com.jmt.wishlist.entities.WishListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface WishListRepository extends JpaRepository<WishList, WishListId>, QuerydslPredicateExecutor<WishList> {
}
