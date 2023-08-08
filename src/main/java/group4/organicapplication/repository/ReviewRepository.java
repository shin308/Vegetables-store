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
}
