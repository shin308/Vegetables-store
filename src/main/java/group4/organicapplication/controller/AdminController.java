package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.*;
import group4.organicapplication.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInUser")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

    @GetMapping
    public String adminPage(Model model){
        return "redirect:admin/statistic";
    }

    @GetMapping("/list-account")
    public String managerAccountPage(Model model) {
        model.addAttribute("listRole", roleService.findAllRole());
        return "account";
    }
    @GetMapping("/list-blacklist-account")
    public String getBlacklistAccount(Model model){
        List<User> users = userService.listBlacklistAccount();

        model.addAttribute("users", users);
        return "admin/blacklistAccount";
    }
    @GetMapping("/restore-blacklist-account/{id}")
    public String restoreBlacklistAccount(@PathVariable long id){
        userService.restoreAccountById(id);
        return "redirect:/admin/list-blacklist-account";
    }

    @GetMapping("delete-blacklist-account/{id}")
    public String deleteBlacklistAccount(@PathVariable long id){
        userService.deleteBlacklistAccountById(id);
        return "redirect:/admin/list-blacklist-account";
    }

    @GetMapping("/orders")
    public String manageOrder(Model model) {
        Set<Role> role = new HashSet<>();
        role.add(roleService.findByRoleName("ROLE_SHIPPER"));

        List<User> shippers = userService.getUserByRole(role);
        for (User shipper : shippers) {
            shipper.setListOrder(orderService.findByOrderStatusAndShipper("Đang giao", shipper));
        }
        model.addAttribute("allShipper", shippers);
        return "admin/order";
    }

    @GetMapping("/sale")
    public String showSalePage(@RequestParam(value = "searchProductName", required = false)String searchProductName,Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        List<Product> products;
        if (searchProductName != null && !searchProductName.isEmpty()) {
            products = productService.findProductByProductName(searchProductName);
        } else {
            products = productService.getAllProduct();
        }
        model.addAttribute("products", products);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalQuantity", cartService.sumQuantity(cartItems));
        model.addAttribute("totalPrice", cartService.sumTotalPrice(cartItems));
        return "sale";
    }
//    @GetMapping("/import")
//    public String showImportPage(){
//        return "import";
//    }

    @GetMapping("/statistic")
    public String showStatisticPage(){
        return "statistic";
    }

    @GetMapping("/profile")
    public String manageProfilrPage(Model model, HttpServletRequest request){
        model.addAttribute("user", getSessionUser(request));
        return "admin/manageProfile";
    }

    @PostMapping("/profile/update")
    public String updateUser(@ModelAttribute User nd, HttpServletRequest request) {
        User currentUser = getSessionUser(request);
        currentUser.setAddress(nd.getAddress());
        currentUser.setLastName(nd.getLastName());
        currentUser.setFirstName(nd.getFirstName());
        currentUser.setPhone(nd.getPhone());
        userService.updateUser(currentUser);
        return "redirect:/admin/profile";
    }

    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }
}
