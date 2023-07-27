package group4.organicapplication.repository;

import group4.organicapplication.model.ImportProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportProductRepository extends JpaRepository<ImportProduct, Integer> {
    @Query("SELECT ip FROM ImportProduct ip WHERE ip.supplier.supplierID = :supplierID")
    List<ImportProduct> findBySupplierID(int supplierID);
}
