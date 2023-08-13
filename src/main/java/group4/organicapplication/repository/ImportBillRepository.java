package group4.organicapplication.repository;

import group4.organicapplication.model.ImportBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportBillRepository extends JpaRepository<ImportBill, Integer> {
    @Query("SELECT ib FROM ImportBill ib JOIN ib.importProduct ip WHERE ip.supplier.supplierID = :supplierID")
    List<ImportBill> findBySupplierID(int supplierID);

    @Query(value = "SELECT ib FROM ImportBill ib WHERE ib.product.productID = :productID")
    ImportBill findByProductID(@Param("productID") int productID);

    @Query(value = "SELECT ib FROM ImportBill ib WHERE ib.importProduct.importID = :importID")
    ImportBill findImportBillsByImportID(@Param("importID") int importID);

    @Query(value = "SELECT ib FROM ImportBill ib JOIN ib.product WHERE ib.product.productName like %:searchProductName% ")
    List<ImportBill> findImportBillByProductName(String searchProductName);
}
