package group4.organicapplication.api.admin;

import group4.organicapplication.dto.AccountDto;
import group4.organicapplication.model.ResponseObject;
import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import group4.organicapplication.service.RoleService;
import group4.organicapplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class AccountApi {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public Page<User> getUserByRole(@RequestParam("roleName") String roleName,
                                           @RequestParam(defaultValue = "1") int page) {
        Set<Role> role = new HashSet<>();
        role.add(roleService.findByRoleName(roleName));

        return userService.getUserByRole(role, page);
    }

    @PostMapping("/save")
    public ResponseObject saveAccount(@RequestBody @Valid AccountDto dto, BindingResult result, Model model) {

        ResponseObject ro = new ResponseObject();

        if(userService.findByEmail(dto.getEmail()) != null) {
            result.rejectValue("email", "error.email","Email đã được đăng ký");
        }
        if(!dto.getConfirmPassword().equals(dto.getPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword","Nhắc lại mật khẩu không đúng");
        }

        if(dto.getPassword().trim().length() == 0){
            result.rejectValue("password", "error.password", "Không được để trống mật khẩu");
        }
        else if(dto.getPassword().length() < 8 || dto.getPassword().length() > 32){
            result.rejectValue("password", "error.password", "Mật khẩu phải dài từ 8 đến 32 ký tự");
        }

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if(dto.getEmail().trim().length() == 0){
            result.rejectValue("email", "error.email", "Không được để trống email");
        }else if(!(pattern.matcher(dto.getEmail()).matches())){
            result.rejectValue("email", "error.email", "Địa chỉ email không hợp lệ");
        }

        if(dto.getFirstName().trim().length() == 0){
            result.rejectValue("firstName", "error.firstName", "Không được để trống tên");
        }
        if(dto.getLastName().trim().length() == 0){
            result.rejectValue("lastName", "error.lastName", "Không được để trống họ");
        }
        if(dto.getAddress().trim().length() == 0){
            result.rejectValue("address", "error.address", "Không được để trống địa chỉ");
        }
        if(dto.getPhone().trim().length() == 0){
            result.rejectValue("phone", "error.phone", "Không được để trống số điện thoại");
        }
        else if(dto.getPhone().trim().length() != 10){
            result.rejectValue("phone", "error.phone", "Hãy nhập đúng số điện thoại");
        }

        if (result.hasErrors()) {
            setErrorsForResponseObject(result, ro);
        } else {
            ro.setStatus("success");
            userService.saveUserForAdmin(dto);
        }
        return ro;
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        userService.deleteById(id);
    }

    public void setErrorsForResponseObject(BindingResult result, ResponseObject object) {

        Map<String, String> errors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        object.setErrorMessages(errors);
        object.setStatus("fail");

        List<String> keys = new ArrayList<>(errors.keySet());
        for (String key: keys) {
            System.out.println(key + ": " + errors.get(key));
        }

        errors = null;
    }
}
