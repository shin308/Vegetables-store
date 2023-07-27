package group4.organicapplication.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class PasswordDto {
    @NotEmpty(message = "Phải nhập mật khẩu cũ")
    private String oldPassword;

    @NotEmpty(message = "Phải nhập mật khẩu mới")
    @Length(min=8, max=32, message="Mật khẩu phải dài 8-32 ký tự")
    private String newPassword;

    @NotEmpty(message = "Phải nhắc lại mật khẩu mới")
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "PasswordDto{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }
}
