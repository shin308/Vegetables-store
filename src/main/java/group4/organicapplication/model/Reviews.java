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
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Column(name = "content", columnDefinition = "nvarchar(200) not null")
    private String content;

    @Column(name = "postDate", nullable = false)
    private Date postDate;

    @Column(name = "star")
    private Integer star;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "replyID")
    private Reviews replyID;

    @OneToMany(mappedBy = "replyID")
    private List<Reviews> reviewsList;

    public Reviews(Product product, User userID, String content) {
        this.product = product;
        this.user = userID;
        this.content = content;
        Date date = new Date();
        this.postDate = date;
    }
}
