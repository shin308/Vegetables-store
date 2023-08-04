package group4.organicapplication.service;

import group4.organicapplication.model.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    List<OrderDetail> save(List<OrderDetail> list);
}
