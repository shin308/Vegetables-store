package group4.organicapplication.api.admin;

import group4.organicapplication.dto.PasswordDto;
import group4.organicapplication.model.ResponseObject;
import group4.organicapplication.model.User;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileApi {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
        User user = userService.findById(id);
        return user;
    }

    @PostMapping("/changePassword")
    public ResponseObject changePass(@RequestBody @Valid PasswordDto dto, BindingResult result,
                                     HttpServletRequest request) {
        System.out.println(dto.toString());
        User currentUser = getSessionUser(request);

        ResponseObject ro = new ResponseObject();

        if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
            result.rejectValue("oldPassword", "error.oldPassword", "Mật khẩu cũ không đúng");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", "error.confirmNewPassword", "Nhắc lại mật khẩu mới không đúng");
        }

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            List<FieldError> errorsList = result.getFieldErrors();
            for (FieldError error : errorsList ) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            ro.setErrorMessages(errors);
            ro.setStatus("fail");
            errors = null;
        } else {
            userService.changePass(currentUser, dto.getNewPassword());
            ro.setStatus("success");
        }

        return ro;
    }

    public User getSessionUser(HttpServletRequest request){
        return (User) request.getSession().getAttribute("loggedInUser");
    }

}
