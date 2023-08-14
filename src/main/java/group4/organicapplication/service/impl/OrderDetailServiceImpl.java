package group4.organicapplication.service.impl;

import group4.organicapplication.model.PurchaseOrder;
import group4.organicapplication.repository.OrderDetailRepository;
import group4.organicapplication.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<PurchaseOrder> save(List<PurchaseOrder> orderDetailList){
        return orderDetailRepository.saveAll(orderDetailList);
    }

    @Override
    public List<Integer> getProductOfOrder(Long orderID) {
        return orderDetailRepository.getProductID(orderID);
    }

    @Override
    public Long sumProductOrder(int productID) {
        return orderDetailRepository.getQuantityProductSale(productID);
    }
}
