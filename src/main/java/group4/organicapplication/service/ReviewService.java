package group4.organicapplication.service;

import group4.organicapplication.model.Reviews;
import group4.organicapplication.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;

    public List<Reviews> getReviewProduct (Integer productID){
        return reviewRepository.findByProductID(productID);
    };
}
