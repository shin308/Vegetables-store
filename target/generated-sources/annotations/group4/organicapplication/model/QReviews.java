package group4.organicapplication.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviews is a Querydsl query type for Reviews
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviews extends EntityPathBase<Reviews> {

    private static final long serialVersionUID = -1968048442L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviews reviews = new QReviews("reviews");

    public final StringPath content = createString("content");

    public final NumberPath<Long> orderID = createNumber("orderID", Long.class);

    public final DateTimePath<java.util.Date> postDate = createDateTime("postDate", java.util.Date.class);

    public final QProduct product;

    public final QReviews replyID;

    public final NumberPath<Long> reviewID = createNumber("reviewID", Long.class);

    public final ListPath<Reviews, QReviews> reviewsList = this.<Reviews, QReviews>createList("reviewsList", Reviews.class, QReviews.class, PathInits.DIRECT2);

    public final NumberPath<Integer> star = createNumber("star", Integer.class);

    public final QUser user;

    public QReviews(String variable) {
        this(Reviews.class, forVariable(variable), INITS);
    }

    public QReviews(Path<? extends Reviews> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviews(PathMetadata metadata, PathInits inits) {
        this(Reviews.class, metadata, inits);
    }

    public QReviews(Class<? extends Reviews> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.replyID = inits.isInitialized("replyID") ? new QReviews(forProperty("replyID"), inits.get("replyID")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

