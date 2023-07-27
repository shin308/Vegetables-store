package group4.organicapplication.repository;

import group4.organicapplication.model.ImportBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportBillRepository extends JpaRepository<ImportBill, Integer> {
    @Query("SELECT ib FROM ImportBill ib JOIN ib.importProduct ip WHERE ip.supplier.supplierID = :supplierID")
    List<ImportBill> findBySupplierID(int supplierID);
}
