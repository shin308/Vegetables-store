package group4.organicapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String productId;
    private String productName;
    private int quantity;
    private int unitPrice;
    private int totalPrice;
    private String imageProduct;
    private int limitQuantity;
}
