package group4.organicapplication.controller;

import group4.organicapplication.model.Category;
import group4.organicapplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private CategoryService service;
    @GetMapping("")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/productInfo")
    public String showproductInfoPage(){
        return "productInfo";
    }

    @GetMapping("/sale")
    public String showSalePage(){
        return "sale";
    }

    @GetMapping("/order")
    public String showOrderPage(){
        return "order";
    }

    @GetMapping("/supplier")
    public String showSupplierPage(){
        return "supplier";
    }

    @GetMapping("/import")
    public String showImportPage(){
        return "import";
    }

    @GetMapping("/account")
    public String showAccountPage(){
        return "account";
    }
}
