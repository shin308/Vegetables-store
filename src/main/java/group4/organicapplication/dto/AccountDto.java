package group4.organicapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class AccountDto {
	private String id;
	@NotEmpty(message = "Tên không được để trống")
	private String firstName;
	@NotEmpty(message = "Họ không được để trống")
	private String lastName;
	@NotEmpty(message = "Phải nhập địa chỉ email")
	@Email(message = "Phải nhập đúng địa chỉ mail")
	private String email;
	@Length(min=8, max=32, message="mật khẩu phải dài 8-32 ký tự")
	private String password;
	private String confirmPassword;
	@NotEmpty(message="Địa chỉ không được trống")
	private String address;
	@NotEmpty(message="Số điện thoại không được để được trống")
	private String phone;
	private String roleName;
//	private String cccd;
	
	public AccountDto(){
		
	}
	
//	public UserRegistrationDto(String firstName, String lastName, String email, String password, String address, String phone, String roleName) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.address = address;
//		this.phone = phone;
////		this.cccd = cccd;
//		this.roleName = roleName;
//	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
