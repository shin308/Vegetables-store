package group4.organicapplication.service;

import group4.organicapplication.dto.AccountDto;
import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface UserService {
//	User save(UserRegistrationDto registrationDto);
//	List<User> findAllUsers();

	User findByConfirmationToken(String confirmationToken);

	User saveUserForMember(User userDto);

	User saveUserForAdmin(AccountDto user);

	User findById(long id);

	Page<User> getUserByRole(Set<Role> role, int page);

	List<User> getUserByRole(Set<Role> role);

	User findByEmail(String email);

	void deleteById(long id);

	void changePass(User nd, String newPass);

	User updateUser(User user);
}
