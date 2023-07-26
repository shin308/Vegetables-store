package group4.organicapplication.controller;

import group4.organicapplication.model.Product;
import group4.organicapplication.service.SelectProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SelectProductController {
    @Autowired private SelectProductService service;

    @GetMapping("/product")
    public String showProductPage(Model model){
        List<Product> productList = service.selectAll();
        model.addAttribute("productList",productList);
        return "product";
    }
}
