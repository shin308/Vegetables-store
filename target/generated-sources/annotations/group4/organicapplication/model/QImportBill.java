package group4.organicapplication.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImportBill is a Querydsl query type for ImportBill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImportBill extends EntityPathBase<ImportBill> {

    private static final long serialVersionUID = 192490177L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImportBill importBill = new QImportBill("importBill");

    public final DatePath<java.util.Date> expiry = createDate("expiry", java.util.Date.class);

    public final QImportBill_ImportBillId id;

    public final NumberPath<Double> importPrice = createNumber("importPrice", Double.class);

    public final QImportProduct importProduct;

    public final QProduct product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Double> totalAmount = createNumber("totalAmount", Double.class);

    public final StringPath unit = createString("unit");

    public QImportBill(String variable) {
        this(ImportBill.class, forVariable(variable), INITS);
    }

    public QImportBill(Path<? extends ImportBill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImportBill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImportBill(PathMetadata metadata, PathInits inits) {
        this(ImportBill.class, metadata, inits);
    }

    public QImportBill(Class<? extends ImportBill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QImportBill_ImportBillId(forProperty("id")) : null;
        this.importProduct = inits.isInitialized("importProduct") ? new QImportProduct(forProperty("importProduct"), inits.get("importProduct")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

