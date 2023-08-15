package group4.organicapplication.service;

import group4.organicapplication.model.PurchaseOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    List<PurchaseOrder> save(List<PurchaseOrder> list);

    List<Integer> getProductOfOrder(Long orderID);

    String sumProductOrder(int productID);
}
