package group4.organicapplication.repository;



import group4.organicapplication.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findByPhone(String phone);
    List<Supplier> findBySupplierNameContainingIgnoreCase(String searchName);
}

