package group4.organicapplication.repository;

import group4.organicapplication.model.Reviews;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    @Query("SELECT p FROM Reviews p where p.product.productID = ?1")
    List<Reviews> findByProductID(Integer productID);

    @Query("SELECT p FROM Reviews p where p.replyID.reviewID = :reviewID")
    List<Reviews> findByReplyID(Long reviewID);

    @Modifying
    @Transactional
    @Query("delete Reviews r where r.reviewID = :reviewID or r.replyID.reviewID = :reviewID")
    void deleteByReviewID(@Param("reviewID") Long reviewID);

    @Modifying
    @Transactional
    @Query(value = "insert into Reviews(productID, userID, content, postDate, replyID, star, order_id) " +
            "values (:productID, :userID ,:content, GETDATE(), null, :star, :orderID)", nativeQuery = true)
    void addNewReview(@Param("productID") String productID,
                      @Param("userID") Long userID,
                      @Param("content") String content,
                      @Param("star") String star,
                      @Param("orderID") Long orderID);

    @Query("select distinct r.orderID from Reviews r")
    List<Long> checkOrderReview();

    @Query("select round(avg(r.star), 1)  from Reviews r where r.product.productID = ?1")
    Float avgStarProduct(Integer productID);

    @Query("select count(r.reviewID) from Reviews r where r.product.productID = ?1 and r.star is not null")
    int countReviewProduct(Integer productID);
}
