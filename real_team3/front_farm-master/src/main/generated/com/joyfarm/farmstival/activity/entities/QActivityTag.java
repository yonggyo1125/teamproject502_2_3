package com.joyfarm.farmstival.activity.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivityTag is a Querydsl query type for ActivityTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivityTag extends EntityPathBase<ActivityTag> {

    private static final long serialVersionUID = 1798322927L;

    public static final QActivityTag activityTag = new QActivityTag("activityTag");

    public final ListPath<Activity, QActivity> items = this.<Activity, QActivity>createList("items", Activity.class, QActivity.class, PathInits.DIRECT2);

    public final StringPath tag = createString("tag");

    public QActivityTag(String variable) {
        super(ActivityTag.class, forVariable(variable));
    }

    public QActivityTag(Path<? extends ActivityTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivityTag(PathMetadata metadata) {
        super(ActivityTag.class, metadata);
    }

}

