package group4.organicapplication.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = -1939022118L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final StringPath address = createString("address");

    public final DateTimePath<java.util.Date> deliveryDay = createDateTime("deliveryDay", java.util.Date.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath note = createString("note");

    public final DateTimePath<java.util.Date> orderDay = createDateTime("orderDay", java.util.Date.class);

    public final ListPath<PurchaseOrder, QPurchaseOrder> orderDetailList = this.<PurchaseOrder, QPurchaseOrder>createList("orderDetailList", PurchaseOrder.class, QPurchaseOrder.class, PathInits.DIRECT2);

    public final StringPath orderStatus = createString("orderStatus");

    public final StringPath phone = createString("phone");

    public final DateTimePath<java.util.Date> receiveDay = createDateTime("receiveDay", java.util.Date.class);

    public final QUser shipper;

    public final NumberPath<Long> totalPrice = createNumber("totalPrice", Long.class);

    public final QUser user;

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shipper = inits.isInitialized("shipper") ? new QUser(forProperty("shipper")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

