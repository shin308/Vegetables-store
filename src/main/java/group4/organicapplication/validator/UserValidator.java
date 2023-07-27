package group4.organicapplication.validator;

import group4.organicapplication.model.User;
import group4.organicapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmpty(errors, "firstName", "error.firstName", "Tên không được bỏ trống");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "error.lastName", "Tên không được bỏ trống");
        ValidationUtils.rejectIfEmpty(errors, "phone", "error.phone", "Số điện thoại không được bỏ trống");
        ValidationUtils.rejectIfEmpty(errors, "address", "error.address", "Địa chỉ không được bỏ trống");

        ValidationUtils.rejectIfEmpty(errors, "email", "error.email", "Email không được để trống");

        //Check địa chỉ email có hợp lệ không
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (!(pattern.matcher(user.getEmail()).matches())){
            errors.rejectValue("email", "error.email", "Địa chỉ email không phù hợp");
        }

        //Check địa chỉ email đã được dùng chưa
        if (userService.findByEmail(user.getEmail()) != null){
            errors.rejectValue("email", "error.email","Email này đã được sử dụng");
        }

        //Check password có trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password","Password không được để trống");

        //Check confirmpassword có trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.confirmPassword","Confirm password không được để trống");

        //Check độ dài của password có nằm trong  khoảng 8-32 hay không
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32){
            errors.rejectValue("password", "error.password", "Mật khẩu phải dài 8 đến 32 ký tự");
        }

        //Kiểm tra confirm password có giống với password hay không
        if (!user.getConfirmPassword().equals(user.getPassword())){
            errors.rejectValue("confirmPassword", "error.confirmPassword", "Nhắc lại mật khẩu không chính xác");
        }
    }
}
