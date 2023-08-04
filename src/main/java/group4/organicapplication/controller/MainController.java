package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.model.Product;
import group4.organicapplication.model.User;
import group4.organicapplication.service.CategoryService;
import group4.organicapplication.service.ProductService;
import group4.organicapplication.service.SelectProductService;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired private SelectProductService selectProductService;

    @Autowired private CategoryService categoryService;

    @Autowired private ProductService productService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }
    public User getSessionUser(HttpServletRequest httpServletRequest){
        return (User) httpServletRequest.getSession().getAttribute("loggedInUser");
    }

    @GetMapping("/")
    public String showHomePage(@RequestParam(value = "searchProductName", required = false)String searchProductName,Model model){
        List<Category> categoryList = categoryService.listAll();
        model.addAttribute(("categoryList"),categoryList);

        List<Product> productList;
        if (searchProductName != null && !searchProductName.isEmpty()) {
            productList = selectProductService.findProductByName(searchProductName);
        } else {
            // Nếu không có tên để tìm kiếm, hiển thị tất cả nhà cung cấp
            productList = selectProductService.selectAll();
        }
        model.addAttribute("productList",productList);
        return "client/home";
    }

    @GetMapping("/{categoryID}")
    public  String selectProductByCategory(@PathVariable("categoryID") Integer categoryID, Model model){
        List<Category> categoryList = categoryService.listAll();
        model.addAttribute(("categoryList"),categoryList);

        Category category = null;
        try {
            category = categoryService.get(categoryID);
        } catch (CategoryNotFoundException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("category",category);

        List<Product> productByCategory = productService.getProductsByCategoryId(categoryID);
        model.addAttribute("productByCategory",productByCategory);
        return "category_user";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        return "client/login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             HttpSession httpSession){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse,auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/productInfo")
    public String showproductInfoPage(){
        return "productInfo";
    }

    @GetMapping("/productInfoUser/{productID}")
    public String showProductInfoUser(@PathVariable("productID") Integer productID, Model model){
        List<Category> categoryList = categoryService.listAll();
        model.addAttribute(("categoryList"),categoryList);

        Product productInfo = productService.get(productID);
        model.addAttribute("productInfo",productInfo);
        return "productInfo_user";
    }

    @GetMapping("/sale")
    public String showSalePage(){
        return "sale";
    }

    @GetMapping("/order")
    public String showOrderPage(){
        return "order";
    }

    @GetMapping("/order_user")
    public String showOrderUserPage(Model model){
        List<Category> categoryList = categoryService.listAll();
        model.addAttribute(("categoryList"),categoryList);
        return "order_user";
    }

    @GetMapping("/cart")
    public String showCartPage(){
        return "cart";
    }


    @GetMapping("/supplier")
    public String showSupplierPage(){
        return "supplier";
    }

    @GetMapping("/import")
    public String showImportPage(){
        return "import";
    }

//    @GetMapping("/account")
//    public String showAccountPage(){
//        return "account";
//    }
}
