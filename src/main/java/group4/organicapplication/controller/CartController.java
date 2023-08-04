package group4.organicapplication.controller;


import group4.organicapplication.model.CartItem;
import group4.organicapplication.service.CartService;
import group4.organicapplication.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller

@RequestMapping("/")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem, HttpSession session) {

        List<CartItem> cartItems = cartService.getCartItems();
        for(CartItem cartItem1 : cartItems) {
            if(cartItem1.getProductId().equals(cartItem.getProductId())) {
                if(cartItem1.getQuantity() >= cartItem1.getLimitQuantity()) {
                    return ResponseEntity.status(400).body("Hết hàng");
                }
            }
        }
        cartService.addToCart(cartItem);
        //session.setMaxInactiveInterval(5);

        return ResponseEntity.ok().build();

    }

    @PostMapping("cart/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody Map<String, String> requestBody) {
        String productId = requestBody.get("productId");
        cartService.removeFromCart(productId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("cart/update")
    public ResponseEntity<String> updateCartItem(@RequestBody Map<String, String> requestBody) {
        String productId = requestBody.get("productId");
        String quantity = requestBody.get("quantity");
        cartService.updateCartItem(productId, Integer.parseInt(quantity));
        return ResponseEntity.ok().build();
    }







}
