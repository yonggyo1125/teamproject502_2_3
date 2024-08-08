package com.jmt.restaurant.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodMenuImage is a Querydsl query type for FoodMenuImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodMenuImage extends EntityPathBase<FoodMenuImage> {

    private static final long serialVersionUID = -1201017608L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFoodMenuImage foodMenuImage = new QFoodMenuImage("foodMenuImage");

    public final StringPath foodImgUrl = createString("foodImgUrl");

    public final QFoodMenu foodMenu;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QFoodMenuImage(String variable) {
        this(FoodMenuImage.class, forVariable(variable), INITS);
    }

    public QFoodMenuImage(Path<? extends FoodMenuImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFoodMenuImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFoodMenuImage(PathMetadata metadata, PathInits inits) {
        this(FoodMenuImage.class, metadata, inits);
    }

    public QFoodMenuImage(Class<? extends FoodMenuImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodMenu = inits.isInitialized("foodMenu") ? new QFoodMenu(forProperty("foodMenu"), inits.get("foodMenu")) : null;
    }

}

