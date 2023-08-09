package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import group4.organicapplication.service.CategoryService;
import group4.organicapplication.service.OrderService;
import group4.organicapplication.service.RoleService;
import group4.organicapplication.service.UserService;
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

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

    @GetMapping
    public String adminPage(Model model){
        return "admin/index";
    }

    @GetMapping("/list-account")
    public String managerAccountPage(Model model) {
        model.addAttribute("listRole", roleService.findAllRole());
        return "account";
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
    public String showSalePage(){
        return "sale";
    }

    @GetMapping("/import")
    public String showImportPage(){
        return "import";
    }

    @GetMapping("/statistic")
    public String showStatisticPage(Model model){
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
