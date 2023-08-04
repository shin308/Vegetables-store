package group4.organicapplication.service.impl;

import group4.organicapplication.model.OrderDetail;
import group4.organicapplication.repository.OrderDetailRepository;
import group4.organicapplication.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> save(List<OrderDetail> orderDetailList){
        return orderDetailRepository.saveAll(orderDetailList);
    }
}
