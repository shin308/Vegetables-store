package group4.organicapplication.repository;

import group4.organicapplication.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    @Query("SELECT p FROM Reviews p where p.product.productID = ?1")
    public List<Reviews> findByProductID(Integer productID);

}
