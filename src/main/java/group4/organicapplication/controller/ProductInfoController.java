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

}
