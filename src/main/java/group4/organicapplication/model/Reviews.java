package group4.organicapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewID")
    private long reviewID;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "id", nullable = false)
    private User userID;

    @Column(name = "content", columnDefinition = "nvarchar(200) not null")
    private String content;

    @Column(name = "postDate", nullable = false)
    private Date postDate;

    @Column(name = "star", nullable = false)
    private Integer star;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "replyID")
    private Reviews replyID;

    @OneToMany(mappedBy = "replyID")
    private List<Reviews> reviewsList;
}
