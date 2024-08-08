package com.jmt.restaurant.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = 938542339L;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final com.jmt.global.entities.QBaseEntity _super = new com.jmt.global.entities.QBaseEntity(this);

    public final StringPath areaNm = createString("areaNm");

    public final NumberPath<Double> bsnsLcncNm = createNumber("bsnsLcncNm", Double.class);

    public final StringPath bsnsTmCn = createString("bsnsTmCn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Double> dbsnsStatmBzcndNm = createNumber("dbsnsStatmBzcndNm", Double.class);

    public final BooleanPath dcrnYn = createBoolean("dcrnYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final BooleanPath delvSrvicYn = createBoolean("delvSrvicYn");

    public final BooleanPath dsbrCvntlYn = createBoolean("dsbrCvntlYn");

    public final BooleanPath fgggMenuOfrYn = createBoolean("fgggMenuOfrYn");

    public final BooleanPath hmdlvSaleYn = createBoolean("hmdlvSaleYn");

    public final StringPath hmpgUrl = createString("hmpgUrl");

    public final ListPath<RestaurantImage, QRestaurantImage> images = this.<RestaurantImage, QRestaurantImage>createList("images", RestaurantImage.class, QRestaurantImage.class, PathInits.DIRECT2);

    public final BooleanPath kioskYn = createBoolean("kioskYn");

    public final BooleanPath mbPmamtYn = createBoolean("mbPmamtYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath onlineRsrvInfoCn = createString("onlineRsrvInfoCn");

    public final BooleanPath petEntrnPosblYn = createBoolean("petEntrnPosblYn");

    public final NumberPath<Integer> prdlSeatCnt = createNumber("prdlSeatCnt", Integer.class);

    public final BooleanPath prkgPosYn = createBoolean("prkgPosYn");

    public final StringPath reprsntMenuNm = createString("reprsntMenuNm");

    public final StringPath restdyInfoCn = createString("restdyInfoCn");

    public final StringPath rsrvMthdNm = createString("rsrvMthdNm");

    public final NumberPath<Long> rstrId = createNumber("rstrId", Long.class);

    public final StringPath rstrIntrcnCont = createString("rstrIntrcnCont");

    public final NumberPath<Double> rstrLa = createNumber("rstrLa", Double.class);

    public final StringPath rstrLnnoAdres = createString("rstrLnnoAdres");

    public final NumberPath<Double> rstrLo = createNumber("rstrLo", Double.class);

    public final StringPath rstrNm = createString("rstrNm");

    public final StringPath rstrRdnmAdr = createString("rstrRdnmAdr");

    public final NumberPath<Double> rstrTelNo = createNumber("rstrTelNo", Double.class);

    public final NumberPath<Integer> seatCnt = createNumber("seatCnt", Integer.class);

    public final BooleanPath smorderYn = createBoolean("smorderYn");

    public final StringPath tlromInfoCn = createString("tlromInfoCn");

    public final BooleanPath wifiOfrYn = createBoolean("wifiOfrYn");

    public QRestaurant(String variable) {
        super(Restaurant.class, forVariable(variable));
    }

    public QRestaurant(Path<? extends Restaurant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurant(PathMetadata metadata) {
        super(Restaurant.class, metadata);
    }

}

