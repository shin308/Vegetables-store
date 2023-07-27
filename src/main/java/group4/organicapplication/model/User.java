package group4.organicapplication.model;

//import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private String email;
	@JsonIgnore
	private String password;

	@Transient
	@JsonIgnore
	private String confirmPassword;
	private String address;
	private String phone;

//	private String cccd;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
		            name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
				            name = "role_id", referencedColumnName = "id"))
	
	private Set<Role> role;
	
	public User() {
		
	}
	
//	public User(String firstName, String lastName, String email, String password, String address,
//				String phone, Set<Role> roles) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.address =  address;
//		this.phone = phone;
////		this.cccd = cccd;
//		this.roles = roles;
//	}
	public User(String email, String password){
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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

//	public String getCccd() {
//		return cccd;
//	}
//	public void setCccd(String cccd) {
//		this.cccd = cccd;
//	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", confirmPassword='" + confirmPassword + '\'' +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}
