package group4.organicapplication.controller;

import group4.organicapplication.model.User;
import group4.organicapplication.service.RoleService;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInUser")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

    @GetMapping
    public String adminPage(Model model){
        return "admin/home";
    }

    @GetMapping("/list-account")
    public String managerAccountPage(Model model) {
        model.addAttribute("listRole", roleService.findAllRole());
        return "admin/manageAccount";
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
        currentUser.setPassword(nd.getPhone());
        userService.updateUser(currentUser);
        return "redirect:/admin/profile";
    }

    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }
}
