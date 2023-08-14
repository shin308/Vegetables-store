package group4.organicapplication.controller;

import group4.organicapplication.model.Reviews;
import group4.organicapplication.service.ProductService;
import group4.organicapplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class ProductInfoUserController {
    @Autowired
    ProductService productService;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/productInfoUser/{productID}/delete/{reviewID}")
    public String deleteReview(@PathVariable("reviewID") Long reviewID, @PathVariable("productID") Integer productID ){
        reviewService.delete(reviewID);
        return "redirect:/productInfoUser/{productID}";
    }

    @PostMapping("/productInfoUser/{productID}/newComment")
    public String newComment(@ModelAttribute Reviews reviews, @PathVariable("productID") Integer productID){
        reviewService.addNewComment(reviews.getProduct(), reviews.getUser(), reviews.getContent());
        return "redirect:/productInfoUser/{productID}";
    }

    @PostMapping("/productInfoUser/{productID}/newReply")
    public String newReply(@ModelAttribute Reviews reviews, @PathVariable("productID") Integer productID, Reviews replyID){
        reviewService.addNewReply(reviews.getProduct(), reviews.getUser(), reviews.getContent(), reviews.getReplyID());
        return "redirect:/productInfoUser/{productID}";
    }
}
