package group4.organicapplication.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImportProduct is a Querydsl query type for ImportProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImportProduct extends EntityPathBase<ImportProduct> {

    private static final long serialVersionUID = 493982709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImportProduct importProduct = new QImportProduct("importProduct");

    public final DatePath<java.util.Date> importDate = createDate("importDate", java.util.Date.class);

    public final NumberPath<Integer> importID = createNumber("importID", Integer.class);

    public final QSupplier supplier;

    public QImportProduct(String variable) {
        this(ImportProduct.class, forVariable(variable), INITS);
    }

    public QImportProduct(Path<? extends ImportProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImportProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImportProduct(PathMetadata metadata, PathInits inits) {
        this(ImportProduct.class, metadata, inits);
    }

    public QImportProduct(Class<? extends ImportProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.supplier = inits.isInitialized("supplier") ? new QSupplier(forProperty("supplier")) : null;
    }

}

