package group4.organicapplication.service.impl;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import group4.organicapplication.dto.SearchOrderObject;
import group4.organicapplication.model.Orders;
import group4.organicapplication.model.QOrders;
import group4.organicapplication.model.User;
import group4.organicapplication.repository.OrderRepository;
import group4.organicapplication.service.OrderService;
import org.attoparser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.querydsl.core.BooleanBuilder;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Page<Orders> getAllOrderByFilter(SearchOrderObject object, int page) throws ParseException {
        BooleanBuilder builder = new BooleanBuilder();

        String trangThaiDon = object.getStatusOrder();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

        if (!trangThaiDon.equals("")) {
            builder.and(QOrders.orders.orderStatus.eq(trangThaiDon));
        }

        return orderRepository.findAll(builder, PageRequest.of(page - 1, 6));
    }

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
    public List<Orders> findByOrderStatusAndShipper(String orderStatus, User shipper){
        return orderRepository.findByOrderStatusAndShipper(orderStatus, shipper);
    }

    @Override
    public int countByOrderStatus(String orderStatus){
        return orderRepository.countByOrderStatus(orderStatus);
    }

}
