package group4.organicapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private long quantity;
    private int totalAmount;

    public Product getProduct() {
        return product;
    }

    public Orders getOrder() {
        return order;
    }

    public long getQuantity() {
        return quantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}

