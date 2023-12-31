package group4.organicapplication.service;

import group4.organicapplication.model.Product;
import group4.organicapplication.model.Reviews;
import group4.organicapplication.model.User;
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

    public  List<Reviews> getReplyOfReview (Long reviewID){
        return reviewRepository.findByReplyID(reviewID);
    }

    public void delete(Long reviewID){
        reviewRepository.deleteByReviewID(reviewID);
    }

    public Reviews addNewComment(Product product, User userID, String content){
        Reviews comment = new Reviews(product, userID, content);
        return reviewRepository.save(comment);
    }

    public Reviews addNewReply(Product product, User userID, String content, Reviews replyID){
        Reviews reply = new Reviews(product, userID, content, replyID);
        return reviewRepository.save(reply);
    }

    public void addNewReview(String product, Long userID, String content, String star, Long orderID){
        reviewRepository.addNewReview(product, userID,content,star, orderID);
    };

    public List<Long> getOrderIDReviewed(){
        return reviewRepository.checkOrderReview();
    }

    public String getAvgStarProduct(int productID){
        return reviewRepository.avgStarProduct(productID);
    }

    public String getQuantityReview(int productID){
        return reviewRepository.countReviewProduct(productID);
    }

    public List<Reviews> getReviewInfo(long orderID){return reviewRepository.getInfoReview(orderID);}
}
