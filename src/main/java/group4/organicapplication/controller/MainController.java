package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.*;
import group4.organicapplication.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"loggedInUser","cartItems"})
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired private SelectProductService selectProductService;

    @Autowired private CategoryService categoryService;

    @Autowired private ProductService productService;

    @Autowired private CartService cartService;

    @Autowired private OrderService orderService;
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
        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute(("categoryList"),categoryList);

        List<Product> productList;
        if (searchProductName != null && !searchProductName.isEmpty()) {
            productList = selectProductService.findProductByName(searchProductName);
        } else {
            // Nếu không có tên để tìm kiếm, hiển thị tất cả sản phẩm
            productList = selectProductService.selectAll();
        }
        model.addAttribute("productList",productList);
        List<CartItem> cartItems = cartService.getCartItems();
        int totalQuantity = cartService.sumQuantity(cartItems);
        model.addAttribute("totalQuantity", totalQuantity);

        return "client/home";
    }

    @GetMapping("/{categoryID}")
    public  String selectProductByCategory(@PathVariable("categoryID") Integer categoryID, Model model){
        List<Category> categoryList = categoryService.listCategory();
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

    @GetMapping("/productInfoUser/{productID}")
    public String showproductInfoUser(@PathVariable("productID") Integer productID, Model model){
        Product productInfo = productService.get(productID);
        model.addAttribute("productInfo",productInfo);
        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute(("categoryList"),categoryList);

        List<CartItem> cartItems = cartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalQuantity", cartService.sumQuantity(cartItems));
        model.addAttribute("totalPrice", cartService.sumTotalPrice(cartItems));

        int quantityInCart = 0;
        String productIDStr = String.valueOf(productID);
        for (CartItem cartItem : cartItems) {

            if (cartItem.getProductId().equals(productIDStr)) {
                quantityInCart = cartItem.getQuantity();
                break;
            }
        }
        model.addAttribute("quantityInCart", quantityInCart);
        return "productInfo_user";
    }

//    @GetMapping("/order")
//    public String showOrderPage(){
//        return "order";
//    }

    @GetMapping("/order_user")
    public String showOrderUserPage(Model model){

        List<CartItem> cartItems = cartService.getCartItems();

        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute(("categoryList"),categoryList);
        List<Orders> orders = orderService.findByUserId(loggedInUser().getId());
        model.addAttribute(("orders"),orders);

        int totalQuantity = cartService.sumQuantity(cartItems);
        model.addAttribute("totalQuantity", totalQuantity);
        return "order_user";
    }

    @GetMapping("/cart")
    public String getCart(@RequestParam(value = "searchProductName", required = false)String searchProductName,Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute(("categoryList"),categoryList);
        List<Product> products;
        if (searchProductName != null && !searchProductName.isEmpty()) {
            products = selectProductService.findProductByName(searchProductName);
        } else {
            // Nếu không có tên để tìm kiếm, hiển thị tất cả nhà cung cấp
            products = selectProductService.selectAll();
        }
        model.addAttribute("products", products);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalQuantity", cartService.sumQuantity(cartItems));
        model.addAttribute("totalPrice", cartService.sumTotalPrice(cartItems));
        return "cart";
    }

    @GetMapping("/purchase")
    public String purchase(@ModelAttribute("cartItems") List<CartItem> cartItems,HttpServletRequest re, Model model) {
        User currentUser = getSessionUser(re);
        model.addAttribute("user", currentUser);

        int totalQuantity = cartService.sumQuantity(cartItems);
        double totalPrice = cartService.sumTotalPrice(cartItems);

        if (totalQuantity == 0) {
            return "redirect:/cart";
        }

        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);

        return "purchase";
    }


//    @GetMapping("/supplier")
//    public String showSupplierPage(){
//        return "supplier";
//    }

//    @GetMapping("/import")
//    public String showImportPage(){
//        return "import";
//    }

//    @GetMapping("/statistic")
//    public String showStatisticPage(){
//        return "statistic";
//    }

//    @GetMapping("/account")
//    public String showAccountPage(){
//        return "account";
//    }
}
