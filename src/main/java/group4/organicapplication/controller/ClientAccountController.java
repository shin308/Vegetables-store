package group4.organicapplication.controller;

import group4.organicapplication.dto.PasswordDto;
import group4.organicapplication.model.CartItem;
import group4.organicapplication.model.ResponseObject;
import group4.organicapplication.model.User;
import group4.organicapplication.service.CartService;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")
public class ClientAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("loggedInUser")
    public User loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

    public User getSessionUser(HttpServletRequest httpServletRequest){
        return (User) httpServletRequest.getSession().getAttribute("loggedInUser");
    }

    @GetMapping("/manage-user-account")
    public String accountPage(HttpServletRequest re, Model model){
        User currentUser = getSessionUser(re);
        model.addAttribute("user", currentUser);

        List<CartItem> cartItems = cartService.getCartItems();
        model.addAttribute("totalQuantity", cartService.sumQuantity(cartItems));

        return "client/manageAccount";
    }
    @GetMapping("/changeInformation")
    public String clientChangeInformationPage(HttpServletRequest res,Model model) {
        User currentUser = getSessionUser(res);
        model.addAttribute("user", currentUser);
        return "client/information";
    }

    @GetMapping("/changePassword")
    public String clientChangePasswordPage() {
        return "client/passwordChange";
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public ResponseObject commitChange(HttpServletRequest res, @RequestBody User ng) {
        User currentUser = getSessionUser(res);
        currentUser.setFirstName(ng.getFirstName());
        currentUser.setLastName(ng.getLastName());
        currentUser.setPhone(ng.getPhone());
        currentUser.setAddress(ng.getAddress());
        userService.updateUser(currentUser);
        return new ResponseObject();
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseObject passwordChange(HttpServletRequest res,@RequestBody PasswordDto dto) {
        User currentUser = getSessionUser(res);
        if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
            ResponseObject re = new ResponseObject();
            re.setStatus("old");
            return re;
        }
        userService.changePass(currentUser, dto.getNewPassword());
        return new ResponseObject();
    }

}
