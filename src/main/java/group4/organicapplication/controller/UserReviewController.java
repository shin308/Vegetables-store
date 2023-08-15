package group4.organicapplication.controller;

import group4.organicapplication.model.Category;
import group4.organicapplication.model.Product;
import group4.organicapplication.model.Reviews;
import group4.organicapplication.model.User;
import group4.organicapplication.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserReviewController {

    @Autowired private ReviewService reviewService;
    @Autowired private OrderDetailService orderDetailService;
    @Autowired private UserService userService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }
    public User getSessionUser(HttpServletRequest httpServletRequest){
        return (User) httpServletRequest.getSession().getAttribute("loggedInUser");
    }

    @PostMapping("/order_user/{orderID}/review")
    public String reviewOrder(@PathVariable("orderID")Long orderID, HttpServletRequest request){
        List<Integer> productIdList = orderDetailService.getProductOfOrder(orderID);
        Long user = loggedInUser().getId();
        for (int j : productIdList) {
            String star = request.getParameter("star" + j) ;
            String content = request.getParameter("content" + j);
            String product = request.getParameter("product" + j) ;
            reviewService.addNewReview(product, user, content, star, orderID);
        }
        return "redirect:/order_user";
    }
}
