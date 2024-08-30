package com.joyfarm.farmstival.activity.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivity is a Querydsl query type for Activity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivity extends EntityPathBase<Activity> {

    private static final long serialVersionUID = -882259797L;

    public static final QActivity activity = new QActivity("activity");

    public final com.joyfarm.farmstival.global.entities.QBaseEntity _super = new com.joyfarm.farmstival.global.entities.QBaseEntity(this);

    public final ListPath<ActivityTag, QActivityTag> acTags = this.<ActivityTag, QActivityTag>createList("acTags", ActivityTag.class, QActivityTag.class, PathInits.DIRECT2);

    public final StringPath activityName = createString("activityName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath division = createString("division");

    public final StringPath doroAddress = createString("doroAddress");

    public final StringPath facilityInfo = createString("facilityInfo");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath manageAgency = createString("manageAgency");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath ownerName = createString("ownerName");

    public final StringPath ownerTel = createString("ownerTel");

    public final StringPath providerCode = createString("providerCode");

    public final StringPath providerName = createString("providerName");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath sido = createString("sido");

    public final StringPath sigungu = createString("sigungu");

    public final StringPath townArea = createString("townArea");

    public final StringPath townImage = createString("townImage");

    public final StringPath townName = createString("townName");

    public final DatePath<java.time.LocalDate> uploadedData = createDate("uploadedData", java.time.LocalDate.class);

    public final StringPath wwwAddress = createString("wwwAddress");

    public QActivity(String variable) {
        super(Activity.class, forVariable(variable));
    }

    public QActivity(Path<? extends Activity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivity(PathMetadata metadata) {
        super(Activity.class, metadata);
    }

}

