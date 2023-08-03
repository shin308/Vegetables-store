package group4.organicapplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private Integer productID;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "img")
    private String img;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description", length = 300)
    private String description;

}
