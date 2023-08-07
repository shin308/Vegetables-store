package group4.organicapplication.controller;

import group4.organicapplication.model.Product;
import group4.organicapplication.model.Reviews;
import group4.organicapplication.service.ProductService;
import group4.organicapplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductInfoController {
    @Autowired
    ProductService productService;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/productInfo/{productID}")
    public String showProductInfo(@PathVariable("productID") Integer productID, Model model){
        Product productInfo = productService.get(productID);
        List<Reviews> reviewAll = reviewService.getReviewProduct(productID);
        model.addAttribute("reviewAll", reviewAll);
        model.addAttribute("productInfo",productInfo);
        return "productInfo";
    }

}
