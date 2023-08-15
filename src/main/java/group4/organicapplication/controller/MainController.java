package group4.organicapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.*;
import group4.organicapplication.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired private ReviewService reviewService;

    @Autowired private OrderDetailService orderDetailService;

    @Autowired private ImportBillService importBillService;
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

    @GetMapping("/categoryByID/{categoryID}")
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

        List<CartItem> cartItems = cartService.getCartItems();
        int totalQuantity = cartService.sumQuantity(cartItems);
        model.addAttribute("totalQuantity", totalQuantity);
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
        ImportBill importBills = importBillService.getImportBillByProductId(productID);

        if (loggedInUser() != null){
            model.addAttribute("userLogged", loggedInUser().getId());
        } else{
            model.addAttribute("userLogged", 0);
        }

        List<CartItem> cartItems = cartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalQuantity", cartService.sumQuantity(cartItems));
        model.addAttribute("totalPrice", cartService.sumTotalPrice(cartItems));
        model.addAttribute("importbill", importBills);

        int quantityInCart = 0;
        String productIDStr = String.valueOf(productID);
        for (CartItem cartItem : cartItems) {

            if (cartItem.getProductId().equals(productIDStr)) {
                quantityInCart = cartItem.getQuantity();
                break;
            }
        }
        model.addAttribute("quantityInCart", quantityInCart);

        List<Reviews> reviewAll = reviewService.getReviewProduct(productID);
        model.addAttribute("reviewAll", reviewAll);
        model.addAttribute("addNew", new Reviews());
        getData(productID, model);
        return "productInfo_user";
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

        List<Long> checkOrderReview = reviewService.getOrderIDReviewed();
        model.addAttribute("checkReview", checkOrderReview);
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
    public String purchase(@ModelAttribute("cartItems") List<CartItem> cartItems,HttpServletRequest re, Model model) throws JsonProcessingException {
        User currentUser = getSessionUser(re);
        model.addAttribute("user", currentUser);

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> mapData = objectMapper.readValue(response.getBody(), new TypeReference<List<Map<String, Object>>>(){});


        model.addAttribute("mapData", mapData);

        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute(("categoryList"),categoryList);

        int totalQuantity = cartService.sumQuantity(cartItems);
        double totalPrice = cartService.sumTotalPrice(cartItems);

        if (totalQuantity == 0) {
            return "redirect:/cart";
        }

        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);

        return "purchase";
    }

    @GetMapping("/order_user/{orderID}/review")
    public String showReviewOrder(@PathVariable("orderID")Long orderID, Model model){
        List<CartItem> cartItems = cartService.getCartItems();
        int totalQuantity = cartService.sumQuantity(cartItems);
        model.addAttribute("totalQuantity", totalQuantity);
        List<Integer> productIdList = orderDetailService.getProductOfOrder(orderID);
        List<Product> products = new ArrayList<>();
        for (int i : productIdList){
            products.add(productService.get(i));
        }
        model.addAttribute("productList", products);
        model.addAttribute("productIdList", productIdList);
        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute(("categoryList"),categoryList);
        return "reviewOrder";
    }

    @GetMapping("/order_user/{orderID}/reviewInfo")
    public String showReviewInfo(@PathVariable("orderID")Long orderID, Model model){
        showReviewOrder(orderID,model);
        List<Reviews> reviewsInfo = reviewService.getReviewInfo(orderID);
        model.addAttribute("reviewInfo", reviewsInfo);
        return "reviewInfo";
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
