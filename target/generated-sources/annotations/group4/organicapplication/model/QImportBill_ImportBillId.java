package group4.organicapplication.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImportBill_ImportBillId is a Querydsl query type for ImportBillId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QImportBill_ImportBillId extends BeanPath<ImportBill.ImportBillId> {

    private static final long serialVersionUID = 436481460L;

    public static final QImportBill_ImportBillId importBillId = new QImportBill_ImportBillId("importBillId");

    public final NumberPath<Integer> importID = createNumber("importID", Integer.class);

    public final NumberPath<Integer> productID = createNumber("productID", Integer.class);

    public QImportBill_ImportBillId(String variable) {
        super(ImportBill.ImportBillId.class, forVariable(variable));
    }

    public QImportBill_ImportBillId(Path<? extends ImportBill.ImportBillId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImportBill_ImportBillId(PathMetadata metadata) {
        super(ImportBill.ImportBillId.class, metadata);
    }

}

