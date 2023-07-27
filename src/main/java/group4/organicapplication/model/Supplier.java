package group4.organicapplication.model;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierID")
    private Integer supplierID;

    @Column(name = "supplierName", nullable = false)
    private String supplierName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", unique = true)
    private String phone;


}
