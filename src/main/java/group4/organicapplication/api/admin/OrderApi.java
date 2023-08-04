package group4.organicapplication.api.admin;

import group4.organicapplication.dto.SearchOrderObject;
import group4.organicapplication.model.Orders;
import group4.organicapplication.service.OrderService;
import group4.organicapplication.service.UserService;
import jakarta.persistence.criteria.Order;
import org.attoparser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Page<Orders> getDonHangByFilter(@RequestParam(defaultValue = "1") int page, @RequestParam String trangThai) throws ParseException {

        SearchOrderObject object = new SearchOrderObject();
        object.setStatusOrder(trangThai);
        Page<Orders> listOrders = orderService.getAllOrderByFilter(object, page);
        return listOrders;
    }

}
