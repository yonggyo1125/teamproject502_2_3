package com.jmt.restaurant.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodMenu is a Querydsl query type for FoodMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodMenu extends EntityPathBase<FoodMenu> {

    private static final long serialVersionUID = 2013375011L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFoodMenu foodMenu = new QFoodMenu("foodMenu");

    public final com.jmt.global.entities.QBaseEntity _super = new com.jmt.global.entities.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final ListPath<FoodMenuImage, QFoodMenuImage> images = this.<FoodMenuImage, QFoodMenuImage>createList("images", FoodMenuImage.class, QFoodMenuImage.class, PathInits.DIRECT2);

    public final StringPath menuCtgryLclasNm = createString("menuCtgryLclasNm");

    public final StringPath menuCtgrySclasNm = createString("menuCtgrySclasNm");

    public final StringPath menuDscrn = createString("menuDscrn");

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final StringPath menuNm = createString("menuNm");

    public final NumberPath<Integer> menuPrice = createNumber("menuPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QRestaurant restaurant;

    public final StringPath spcltMenuNm = createString("spcltMenuNm");

    public final StringPath spcltMenuOgnUrl = createString("spcltMenuOgnUrl");

    public final BooleanPath spcltMenuYn = createBoolean("spcltMenuYn");

    public QFoodMenu(String variable) {
        this(FoodMenu.class, forVariable(variable), INITS);
    }

    public QFoodMenu(Path<? extends FoodMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFoodMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFoodMenu(PathMetadata metadata, PathInits inits) {
        this(FoodMenu.class, metadata, inits);
    }

    public QFoodMenu(Class<? extends FoodMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.restaurant = inits.isInitialized("restaurant") ? new QRestaurant(forProperty("restaurant")) : null;
    }

}

