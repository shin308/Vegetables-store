package group4.organicapplication.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSupplier is a Querydsl query type for Supplier
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplier extends EntityPathBase<Supplier> {

    private static final long serialVersionUID = 2012892289L;

    public static final QSupplier supplier = new QSupplier("supplier");

    public final StringPath address = createString("address");

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> supplierID = createNumber("supplierID", Integer.class);

    public final StringPath supplierName = createString("supplierName");

    public QSupplier(String variable) {
        super(Supplier.class, forVariable(variable));
    }

    public QSupplier(Path<? extends Supplier> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSupplier(PathMetadata metadata) {
        super(Supplier.class, metadata);
    }

}

