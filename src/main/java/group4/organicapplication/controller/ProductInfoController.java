package group4.organicapplication.controller;

import group4.organicapplication.model.Product;
import group4.organicapplication.model.Reviews;
import group4.organicapplication.model.User;
import group4.organicapplication.service.ProductService;
import group4.organicapplication.service.ReviewService;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInUser")
public class ProductInfoController {
    @Autowired
    ProductService productService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    private UserService userService;

    @GetMapping("/productInfo/{productID}")
    public String showProductInfo(@PathVariable("productID") Integer productID, Model model){
        Product productInfo = productService.get(productID);
        List<Reviews> reviewAll = reviewService.getReviewProduct(productID);
        model.addAttribute("reviewAll", reviewAll);
        model.addAttribute("productInfo",productInfo);
        model.addAttribute("addComment", new Reviews());
        return "productInfo";
    }

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }
    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }

    @GetMapping("/productInfo/{productID}/delete/{reviewID}")
    public String deleteReview(@PathVariable("reviewID") Long reviewID,@PathVariable("productID") Integer productID ){
        reviewService.delete(reviewID);
        return "redirect:/admin/productInfo/{productID}";
    }

    @GetMapping("/productInfo/{productID}/reply/{reviewID}")
    public String showReply(@PathVariable("reviewID") Long reviewID,@PathVariable("productID") Integer productID, Model model){
        showProductInfo(productID, model);
        List<Reviews> replyAll = reviewService.getReplyOfReview(reviewID);
        model.addAttribute("replyAll", replyAll);
        return "productInfo";
    }

    @PostMapping("/productInfo/{productID}/newComment")
    public String newComment(@ModelAttribute Reviews reviews, @PathVariable("productID") Integer productID){
        Reviews comment = reviewService.addNewComment(reviews.getProduct(), reviews.getUser(), reviews.getContent());
        return "redirect:/admin/productInfo/{productID}";
    }

}
