package group4.organicapplication.repository;

import group4.organicapplication.model.PurchaseOrder;
import group4.organicapplication.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<PurchaseOrder,Long> {

}
