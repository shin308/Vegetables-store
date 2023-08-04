package group4.organicapplication.repository;

import group4.organicapplication.model.Orders;
import group4.organicapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    public List<Orders> findByOrderStatus(String orderStatus);

    public int countByOrderStatus(String orderStatus);
}
