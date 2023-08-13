package group4.organicapplication.api.shipper;

import group4.organicapplication.dto.SearchOrderObject;
import group4.organicapplication.dto.UpdateOrderShipper;
import group4.organicapplication.model.Orders;
import group4.organicapplication.model.PurchaseOrder;
import group4.organicapplication.model.User;
import group4.organicapplication.service.OrderService;
import group4.organicapplication.service.UserService;
import org.attoparser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/shipper/orders")
public class OrdersShipperApi {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Page<Orders> getDonHangByFilter(@RequestParam(defaultValue = "1") int page, @RequestParam String trangThai,
                                           @RequestParam Long idShipper)
            throws ParseException {

        SearchOrderObject object = new SearchOrderObject();
        object.setStatusOrder(trangThai);
//        Long newIdShipper = Long.parseLong(idShipper);
        User shipper = userService.findById(idShipper);
        Page<Orders> listDonHang = orderService.findOrderByShipper(object, page, 6, shipper);
        return listDonHang;
    }

    @GetMapping("/{id}")
    public Orders getDonHangById(@PathVariable long id) {
        return orderService.findById(id);
    }

    @PostMapping("/receive-order")
    public void nhanDonHang(@RequestBody UpdateOrderShipper updateOrderShipper) {
        Orders donHang = orderService.findById(updateOrderShipper.getIdDonHang());

        donHang.setOrderStatus("Đang giao");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            String dateStr = format.format(new Date());
            Date date = format.parse(dateStr);
            donHang.setDeliveryDay(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        orderService.save(donHang);
    }

    @PostMapping("/update")
    public void capNhatTrangThaiDonHang(@RequestBody UpdateOrderShipper updateOrderShipper) {
        Orders donHang = orderService.findById(updateOrderShipper.getIdDonHang());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            String dateStr = format.format(new Date());
            Date date = format.parse(dateStr);
            donHang.setReceiveDay(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        donHang.setOrderStatus("Chờ duyệt");

        String ghiChu = updateOrderShipper.getGhiChuShipper();

        if (!ghiChu.equals("")) {
            donHang.setNote("Ghi chú shipper: \n" + updateOrderShipper.getGhiChuShipper());
        }
        donHang.setImgDelivery(updateOrderShipper.getImgDeliveryOrder());

        orderService.save(donHang);
    }
}
