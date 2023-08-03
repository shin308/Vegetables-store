package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.model.Product;
import group4.organicapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductInfoController {
    @Autowired
    ProductService productService;

    @GetMapping("/productInfo/{productID}")
    public String showProductInfo(@PathVariable("productID") Integer productID, Model model){
        Product productInfo = productService.get(productID);
        model.addAttribute("productInfo",productInfo);
        return "productInfo";
    }

}
