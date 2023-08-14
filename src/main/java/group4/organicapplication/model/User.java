package group4.organicapplication.model;

//import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "[user]", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", columnDefinition = "nvarchar(50)")
	private String firstName;
	
	@Column(name = "last_name", columnDefinition = "nvarchar(50)")
	private String lastName;
	
	private String email;
	@JsonIgnore
	private String password;

	@Transient
	@JsonIgnore
	private String confirmPassword;
	@Column(name = "address", columnDefinition = "nvarchar(200)")
	private String address;
	private String phone;

//	private String cccd;
	
	@ManyToMany
	@JoinTable(name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	
	private Set<Role> role;

	@Transient
	@JsonIgnore
	private List<Orders> listOrders;

	@Column(name = "deleted")
	private int deleted = 0;

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
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

	public String getFullName(){
		return getLastName() + " " + getFirstName();
	}

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

	public List<Orders> getListOrder() {
		return listOrders;
	}

	public void setListOrder(List<Orders> listOrders) {
		this.listOrders = listOrders;
	}

	public User() {
	}

	public User(String email, String password){
		this.email = email;
		this.password = password;
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
