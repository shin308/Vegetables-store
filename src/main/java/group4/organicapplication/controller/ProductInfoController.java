package group4.organicapplication.controller;

import group4.organicapplication.model.Product;
import group4.organicapplication.model.Reviews;
import group4.organicapplication.model.User;
import group4.organicapplication.service.OrderDetailService;
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
    @Autowired private OrderDetailService orderDetailService;

    @GetMapping("/productInfo/{productID}")
    public String showProductInfo(@PathVariable("productID") Integer productID, Model model){
        Product productInfo = productService.get(productID);
        List<Reviews> reviewAll = reviewService.getReviewProduct(productID);
        model.addAttribute("reviewAll", reviewAll);
        model.addAttribute("productInfo",productInfo);
        model.addAttribute("addNew", new Reviews());
        getData(productID, model);
        return "productInfo";
    }

    public void getData(int productID, Model model){
        String starAvg = reviewService.getAvgStarProduct(productID);
        String countReview = reviewService.getQuantityReview(productID);
        String sumQuantity = orderDetailService.sumProductOrder(productID);
        if (starAvg == null ){
            model.addAttribute("starAvg", 0);
        }
        if (countReview == null ){
            model.addAttribute("quantityReview", 0);
        }
        if (sumQuantity == null ){
            model.addAttribute("sumQuantity", 0);
        }
        model.addAttribute("starAvg", starAvg);
        model.addAttribute("quantityReview", countReview);
        model.addAttribute("sumQuantity", sumQuantity);
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

    @PostMapping("/productInfo/{productID}/newReply")
    public String newReply(@ModelAttribute Reviews reviews, @PathVariable("productID") Integer productID, Reviews replyID){
        reviewService.addNewReply(reviews.getProduct(), reviews.getUser(), reviews.getContent(), reviews.getReplyID());
        return "redirect:/admin/productInfo/{productID}";
    }
}
