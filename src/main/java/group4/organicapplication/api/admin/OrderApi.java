package group4.organicapplication.api.admin;

import group4.organicapplication.dto.SearchOrderObject;
import group4.organicapplication.model.Orders;
import group4.organicapplication.model.Product;
import group4.organicapplication.model.PurchaseOrder;
import group4.organicapplication.service.OrderService;
import group4.organicapplication.service.UserService;
import jakarta.persistence.criteria.Order;
import org.attoparser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/{id}")
    public Orders getDonHangById(@PathVariable long id) {
        return orderService.findById(id);
    }

    // phân công đơn hàng
    @PostMapping("/assign")
    public void phanCongDonHang(@RequestParam("shipper") String emailShipper,
                                @RequestParam("donHangId") long donHangId) {
        Orders dh = orderService.findById(donHangId);
        //dh.setOrderStatus("Đang giao");
        dh.setShipper(userService.findByEmail(emailShipper));

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//
//            String dateStr = format.format(new Date());
//            Date date = format.parse(dateStr);
//            dh.setDeliveryDay(date);
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }

        orderService.save(dh);
    }

    // xác nhận hoàn thành đơn hàng
    @PostMapping("/update")
    public void xacNhanHoanThanhDon(@RequestParam("donHangId") long donHangId,
                                    @RequestParam("ghiChu") String ghiChuAdmin) {
        Orders dh = orderService.findById(donHangId);

//        for(PurchaseOrder ct : dh.getOrderDetailList()) {
//            Product sp = ct.getProduct();
//            sp.setDonViBan(sp.getDonViBan() + ct.getSoLuongNhanHang());
//            sp.setDonViKho(sp.getDonViKho() - ct.getSoLuongNhanHang() );
//        }
        dh.setOrderStatus("Hoàn thành");
        String ghiChu = dh.getNote();
        if (!ghiChuAdmin.equals("")) {
            ghiChu += "<br> Ghi chú admin:\n" + ghiChuAdmin;
        }
        dh.setNote(ghiChu);
        orderService.save(dh);
    }

    // xác nhận huỷ đơn hàng
    @PostMapping("/cancel")
    public void huyDonHangAdmin(@RequestParam("donHangId") long donHangId) {
        Orders dh = orderService.findById(donHangId);
        dh.setOrderStatus("Đã bị hủy");
        orderService.save(dh);
    }

    @GetMapping("/statistic-revenue-day")
    public List<Object> revenueDayChart() {
        return orderService.getOrderByDayAndWeek();
    }

    @GetMapping("/statistic-revenue-week")
    public List<Object> revenueWeekChart() {
        return orderService.getOrderByWeekAndMonth();
    }

    @GetMapping("/statistic-revenue-month")
    public List<Object> revenueMonthChart() {
        return orderService.getOrderByMonthAndYear();
    }

}
