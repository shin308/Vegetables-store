package group4.organicapplication.repository;

import group4.organicapplication.model.Orders;
import group4.organicapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>, QuerydslPredicateExecutor<Orders> {
    public List<Orders> findByOrderStatusAndShipper(String orderStatus, User shipper);

    public int countByOrderStatus(String orderStatus);
}
