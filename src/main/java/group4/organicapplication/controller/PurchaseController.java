package group4.organicapplication.controller;

import group4.organicapplication.model.*;
import group4.organicapplication.service.CartService;
import group4.organicapplication.service.OrderService;
import group4.organicapplication.service.UserService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.MimeMessageHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private JavaMailSender javaMailSender;

//    public User getSessionUserPurchase(HttpServletRequest httpServletRequest){
//        return (User) httpServletRequest.getSession().getAttribute("loggedInUser");
//    }
//
//    @PostMapping("/updateInfo")
//    @ResponseBody
//    public ResponseObject commitChangePurchase(HttpServletRequest res, @RequestBody User ng) {
//        User currentUser = getSessionUserPurchase(res);
//        currentUser.setFirstName(ng.getFirstName());
//        currentUser.setLastName(ng.getLastName());
//        currentUser.setPhone(ng.getPhone());
//        currentUser.setAddress(ng.getAddress());
//        userService.updateUser(currentUser);
//        return new ResponseObject();
//    }

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody Orders orders) {

        Orders order = new Orders();

        order.setAddress(orders.getAddress());
        order.setEmail(orders.getEmail());
        order.setNote(orders.getNote());
        order.setOrderDay(orders.getOrderDay());
        order.setTotalPrice(orders.getTotalPrice());
        order.setOrderStatus(orders.getOrderStatus());
        order.setPhone(orders.getPhone());



        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : orders.getOrderDetailList()) {
            PurchaseOrder purchaseOrder1 = new PurchaseOrder();
            purchaseOrder1.setProduct(purchaseOrder.getProduct());
            purchaseOrder1.setQuantity(purchaseOrder.getQuantity());
            purchaseOrder1.setTotalAmount(purchaseOrder.getTotalAmount());
            purchaseOrders.add(purchaseOrder1);
        }

        orderService.placeOrder(order, purchaseOrders);

        List<CartItem> cartItems = cartService.getCartItems();
        cartItems.clear();

        return ResponseEntity.ok().build();

    }


    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, Object> emailData) {
        String email = (String) emailData.get("email");
        String name = (String) emailData.get("name");
        String orderDay = (String) emailData.get("orderDay");
        String phone = (String) emailData.get("phone");
        String address = (String) emailData.get("address");
        String totalPrice = (String) emailData.get("totalPrice");
        int fullPrice = Integer.parseInt(totalPrice) + 5000;

        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) emailData.get("cartItems");


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,  "utf-8");

        try {
            message.setTo(email);
            message.setSubject("Xác nhận đơn hàng");

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Xin chào " + name + ",");
            emailContent.append("<br>");
            emailContent.append("Đơn hàng của bạn đã được đặt thành công.");
            emailContent.append("<br><br>");
            emailContent.append("<b>THÔNG TIN ĐƠN HÀNG:</b><hr>");
            emailContent.append("<br>");
            emailContent.append("Ngày đặt hàng: " +orderDay + "<br>");
            emailContent.append("Số điện thoại: " + phone +"<br>");
            emailContent.append("Địa chỉ giao hàng: " + address + "<br><br>");


            for (Map<String, Object> cartItem : cartItems) {
                emailContent.append("Tên sản phẩm: ").append(cartItem.get("productName")).append("<br>");
                emailContent.append("<img src='https://vanduc19it.github.io/image_vegetable/src/main/resources/static/images/").append(cartItem.get("imageProduct")).append("' width='100' height='100'>").append("<br>");
                emailContent.append("Số lượng: ").append(cartItem.get("quantity")).append("<br>");
                emailContent.append("Thành tiền: ").append(cartItem.get("totalPrice")).append(" đồng").append("<br>");
                emailContent.append("<hr>");
            }
            emailContent.append("<b>Tổng tiền: </b>" + totalPrice +" đồng").append("<br>");
            emailContent.append("<b>Phí vận chuyển:</b> 5000" +" đồng</b>").append("<br>");
            emailContent.append("<b>Tổng thanh toán: </b> " + fullPrice +" đồng").append("<br>");
            emailContent.append("<br>");
            emailContent.append("Vegetables store cảm ơn bạn đã mua hàng!");

            message.setText(emailContent.toString(), true);
            javaMailSender.send(mimeMessage);
            return ResponseEntity.ok("Gửi email thành công.");

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gửi email thất bại.");
        }


    }
}
