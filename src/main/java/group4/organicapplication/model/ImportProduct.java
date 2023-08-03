package group4.organicapplication.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "ImportProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "importID")
    private Integer importID;

    @ManyToOne
    @JoinColumn(name = "supplierID")
    private Supplier supplier;

    @Column(name = "importDate")
    private Date importDate;

}
