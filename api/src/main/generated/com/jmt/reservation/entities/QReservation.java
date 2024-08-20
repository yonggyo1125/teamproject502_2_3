package com.jmt.reservation.entities;

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

    private static final long serialVersionUID = 177697003L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.jmt.global.entities.QBaseEntity _super = new com.jmt.global.entities.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath email = createString("email");

    public final com.jmt.member.entities.QMember member;

    public final StringPath mobile = createString("mobile");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final NumberPath<Long> orderNo = createNumber("orderNo", Long.class);

    public final StringPath payBankAccount = createString("payBankAccount");

    public final StringPath payBankName = createString("payBankName");

    public final StringPath payLog = createString("payLog");

    public final EnumPath<com.jmt.payment.constants.PayMethod> payMethod = createEnum("payMethod", com.jmt.payment.constants.PayMethod.class);

    public final StringPath payTid = createString("payTid");

    public final NumberPath<Integer> persons = createNumber("persons", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath rAddress = createString("rAddress");

    public final DateTimePath<java.time.LocalDateTime> rDateTime = createDateTime("rDateTime", java.time.LocalDateTime.class);

    public final com.jmt.restaurant.entities.QRestaurant restaurant;

    public final StringPath rName = createString("rName");

    public final StringPath rTel = createString("rTel");

    public final EnumPath<com.jmt.reservation.constants.ReservationStatus> status = createEnum("status", com.jmt.reservation.constants.ReservationStatus.class);

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
        this.member = inits.isInitialized("member") ? new com.jmt.member.entities.QMember(forProperty("member")) : null;
        this.restaurant = inits.isInitialized("restaurant") ? new com.jmt.restaurant.entities.QRestaurant(forProperty("restaurant")) : null;
    }

}

