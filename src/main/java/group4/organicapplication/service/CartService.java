package group4.organicapplication.service;

import group4.organicapplication.model.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(CartItem cartItem) {
        if (cartItem == null || cartItem.getProductId() == null) {
            return;
        }

        boolean itemExists = false;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(cartItem.getProductId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                item.setTotalPrice(item.getQuantity() * item.getUnitPrice());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getUnitPrice());
            cartItems.add(cartItem);
        }
    }

    public void removeFromCart(String productId) {
        cartItems.removeIf(item -> item.getProductId().equals(productId));
    }
    public void updateCartItem(String productId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(item.getUnitPrice() * quantity);
                break;
            }
        }
    }

    public int sumQuantity(List<CartItem> cartItems) {
        int sum = 0;
        for (CartItem item : cartItems) {
            sum += item.getQuantity();
        }
        return sum;
    }

    public double sumTotalPrice(List<CartItem> cartItems) {
        int sum = 0;
        for (CartItem item : cartItems) {
            sum += item.getTotalPrice();
        }
        return sum;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}

