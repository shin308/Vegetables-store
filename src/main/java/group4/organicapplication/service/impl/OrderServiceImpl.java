package group4.organicapplication.service.impl;

import group4.organicapplication.model.Orders;
import group4.organicapplication.repository.OrderRepository;
import group4.organicapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Orders updateOrder(Orders order){
        return orderRepository.save(order);
    }

    @Override
    public Orders save(Orders order){
        return orderRepository.save(order);
    }

    @Override
    public Orders findById(long id){
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Orders> findByOrderStatus(String orderStatus){
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public int countByOrderStatus(String orderStatus){
        return orderRepository.countByOrderStatus(orderStatus);
    }

}
