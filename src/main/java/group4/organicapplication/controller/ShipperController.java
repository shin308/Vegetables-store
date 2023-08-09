package group4.organicapplication.controller;

import group4.organicapplication.model.User;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shipper")
@SessionAttributes("loggedInUser")
public class ShipperController {
    @Autowired
    private UserService userService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

    @GetMapping(value = {"", "/orders"})
    public String shipperPage(){
        return "shipper/manageOrder";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, HttpServletRequest request) {
        model.addAttribute("user", getSessionUser(request));
        System.out.println(getSessionUser(request).toString());
        return "shipper/manageProfileShipper";
    }

    @PostMapping("/profile/update")
    public String updateUser(@ModelAttribute User nd, HttpServletRequest request) {
        User currentUser = getSessionUser(request);
        currentUser.setAddress(nd.getAddress());
        currentUser.setLastName(nd.getLastName());
        currentUser.setFirstName(nd.getFirstName());
        currentUser.setPhone(nd.getPhone());
        userService.updateUser(currentUser);
        return "redirect:/shipper/profile";
    }

    public User getSessionUser(HttpServletRequest request){
        return (User) request.getSession().getAttribute("loggedInUser");
    }
}
