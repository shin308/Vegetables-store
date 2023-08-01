package group4.organicapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ImportBill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportBill {

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImportBillId implements Serializable {
        @Column(name = "importID")
        private Integer importID;

        @Column(name = "productID")
        private Integer productID;

    }

    @EmbeddedId
    private ImportBillId id;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "productID")
    private Product product;

    @ManyToOne
    @MapsId("importID")
    @JoinColumn(name = "importID")
    private ImportProduct importProduct;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "expiry")
    private Date expiry;

    @Column(name = "importPrice")
    private Double importPrice;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "totalAmount")
    private Double totalAmount;
}



