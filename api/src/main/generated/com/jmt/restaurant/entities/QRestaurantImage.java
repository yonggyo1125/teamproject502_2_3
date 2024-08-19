package com.jmt.restaurant.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurantImage is a Querydsl query type for RestaurantImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantImage extends EntityPathBase<RestaurantImage> {

    private static final long serialVersionUID = -1472176616L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRestaurantImage restaurantImage = new QRestaurantImage("restaurantImage");

    public final QRestaurant restaurant;

    public final StringPath rstrImgUrl = createString("rstrImgUrl");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QRestaurantImage(String variable) {
        this(RestaurantImage.class, forVariable(variable), INITS);
    }

    public QRestaurantImage(Path<? extends RestaurantImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRestaurantImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRestaurantImage(PathMetadata metadata, PathInits inits) {
        this(RestaurantImage.class, metadata, inits);
    }

    public QRestaurantImage(Class<? extends RestaurantImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.restaurant = inits.isInitialized("restaurant") ? new QRestaurant(forProperty("restaurant")) : null;
    }

}

