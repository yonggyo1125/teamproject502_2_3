package com.joyfarm.farmstival.tour.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTourPlaceTag is a Querydsl query type for TourPlaceTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourPlaceTag extends EntityPathBase<TourPlaceTag> {

    private static final long serialVersionUID = -2134786896L;

    public static final QTourPlaceTag tourPlaceTag = new QTourPlaceTag("tourPlaceTag");

    public final ListPath<TourPlace, QTourPlace> items = this.<TourPlace, QTourPlace>createList("items", TourPlace.class, QTourPlace.class, PathInits.DIRECT2);

    public final StringPath tag = createString("tag");

    public QTourPlaceTag(String variable) {
        super(TourPlaceTag.class, forVariable(variable));
    }

    public QTourPlaceTag(Path<? extends TourPlaceTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTourPlaceTag(PathMetadata metadata) {
        super(TourPlaceTag.class, metadata);
    }

}

