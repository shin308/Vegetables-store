package group4.organicapplication.repository;

import group4.organicapplication.model.ImportProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportProductRepository extends JpaRepository<ImportProduct, Integer> {
    @Query("SELECT ip FROM ImportProduct ip WHERE ip.supplier.supplierID = :supplierID")
    List<ImportProduct> findBySupplierID(int supplierID);

    @Query("SELECT ip FROM ImportProduct ip WHERE ip.importID = :importID")
    ImportProduct findByImportID(@Param("importID") int importID);

//	  @Query("SELECT ip FROM ImportProduct ip JOIN ImportBill ib ON ip.importID = "
//	  		+ ":importID  WHERE ib.product.productID = :productID")
//	  ImportProduct findByProductID(@Param("importID") int importID, @Param("productID") int productID);
//	  ImportProduct findByProductID(@Param("importID") int importID, @Param("productID") int productID);
}
