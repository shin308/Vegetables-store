package group4.organicapplication.repository;

import group4.organicapplication.model.PurchaseOrder;
import group4.organicapplication.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<PurchaseOrder,Long> {
    @Query("select distinct p.product.productID from PurchaseOrder p where p.order.id = ?1")
    List<Integer> getProductID(Long orderID);

    @Query("select sum(p.quantity) from PurchaseOrder p where p.product.productID = ?1")
    String getQuantityProductSale (int productID);
}
