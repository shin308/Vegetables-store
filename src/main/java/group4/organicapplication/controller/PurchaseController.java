package group4.organicapplication.controller;

import group4.organicapplication.model.ResponseObject;
import group4.organicapplication.model.User;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private UserService userService;

    public User getSessionUserPurchase(HttpServletRequest httpServletRequest){
        return (User) httpServletRequest.getSession().getAttribute("loggedInUser");
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public ResponseObject commitChangePurchase(HttpServletRequest res, @RequestBody User ng) {
        User currentUser = getSessionUserPurchase(res);
        currentUser.setFirstName(ng.getFirstName());
        currentUser.setLastName(ng.getLastName());
        currentUser.setPhone(ng.getPhone());
        currentUser.setAddress(ng.getAddress());
        userService.updateUser(currentUser);
        return new ResponseObject();
    }
}
