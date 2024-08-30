package com.joyfarm.farmstival.farmfarm.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFestival is a Querydsl query type for Festival
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFestival extends EntityPathBase<Festival> {

    private static final long serialVersionUID = 750404657L;

    public static final QFestival festival = new QFestival("festival");

    public final com.joyfarm.farmstival.global.entities.QBaseEntity _super = new com.joyfarm.farmstival.global.entities.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath cat1 = createString("cat1");

    public final StringPath cat2 = createString("cat2");

    public final StringPath cat3 = createString("cat3");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final StringPath hostMain = createString("hostMain");

    public final StringPath hostSub = createString("hostSub");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath location = createString("location");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath pageLink = createString("pageLink");

    public final StringPath photoUrl1 = createString("photoUrl1");

    public final StringPath photoUrl2 = createString("photoUrl2");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath tel = createString("tel");

    public final StringPath title = createString("title");

    public QFestival(String variable) {
        super(Festival.class, forVariable(variable));
    }

    public QFestival(Path<? extends Festival> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFestival(PathMetadata metadata) {
        super(Festival.class, metadata);
    }

}

