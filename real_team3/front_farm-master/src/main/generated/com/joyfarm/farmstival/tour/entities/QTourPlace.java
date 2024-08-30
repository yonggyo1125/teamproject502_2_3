package com.joyfarm.farmstival.tour.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourPlace is a Querydsl query type for TourPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourPlace extends EntityPathBase<TourPlace> {

    private static final long serialVersionUID = 594052746L;

    public static final QTourPlace tourPlace = new QTourPlace("tourPlace");

    public final com.joyfarm.farmstival.global.entities.QBaseEntity _super = new com.joyfarm.farmstival.global.entities.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath course = createString("course");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath description = createString("description");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath period = createString("period");

    public final StringPath photoUrl = createString("photoUrl");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath sido = createString("sido");

    public final StringPath sigungu = createString("sigungu");

    public final ListPath<TourPlaceTag, QTourPlaceTag> tags = this.<TourPlaceTag, QTourPlaceTag>createList("tags", TourPlaceTag.class, QTourPlaceTag.class, PathInits.DIRECT2);

    public final StringPath tel = createString("tel");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> tourDays = createNumber("tourDays", Integer.class);

    public QTourPlace(String variable) {
        super(TourPlace.class, forVariable(variable));
    }

    public QTourPlace(Path<? extends TourPlace> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTourPlace(PathMetadata metadata) {
        super(TourPlace.class, metadata);
    }

}

