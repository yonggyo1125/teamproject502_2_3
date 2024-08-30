package com.joyfarm.farmstival.activity.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 1152530608L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.joyfarm.farmstival.global.entities.QBaseEntity _super = new com.joyfarm.farmstival.global.entities.QBaseEntity(this);

    public final QActivity activity;

    public final StringPath activityName = createString("activityName");

    public final EnumPath<com.joyfarm.farmstival.activity.constants.AM_PM> ampm = createEnum("ampm", com.joyfarm.farmstival.activity.constants.AM_PM.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath doroAddress = createString("doroAddress");

    public final StringPath email = createString("email");

    public final com.joyfarm.farmstival.member.entities.QMember member;

    public final StringPath mobile = createString("mobile");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath ownerName = createString("ownerName");

    public final StringPath ownerTel = createString("ownerTel");

    public final NumberPath<Integer> persons = createNumber("persons", Integer.class);

    public final DatePath<java.time.LocalDate> rDate = createDate("rDate", java.time.LocalDate.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final EnumPath<com.joyfarm.farmstival.activity.constants.Status> status = createEnum("status", com.joyfarm.farmstival.activity.constants.Status.class);

    public final StringPath townName = createString("townName");

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.activity = inits.isInitialized("activity") ? new QActivity(forProperty("activity")) : null;
        this.member = inits.isInitialized("member") ? new com.joyfarm.farmstival.member.entities.QMember(forProperty("member")) : null;
    }

}

