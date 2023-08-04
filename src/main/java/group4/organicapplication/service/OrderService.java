package group4.organicapplication.service;

import group4.organicapplication.model.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Orders> findByOrderStatus(String orderStatus);
    int countByOrderStatus(String orderStatus);

    Orders updateOrder(Orders order);

    Orders findById(long id);

    Orders save(Orders order);
}
