package group4.organicapplication.service.impl;

import group4.organicapplication.dto.AccountDto;
import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import group4.organicapplication.repository.RoleRepository;
import group4.organicapplication.repository.UserRepository;
import group4.organicapplication.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	public UserServiceImpl(UserRepository userRepository) {
//		super();
//		this.userRepository = userRepository;
//	}

//	@Override
//	public List<User> finAllUsers(){
//		return userRepository.findAll();
//	}

//	@Override
//	public User save(UserRegistrationDto registrationDto) {
//		User user = new User(registrationDto.getFirstName(),
//				registrationDto.getLastName(), registrationDto.getEmail(),
//				passwordEncoder.encode(registrationDto.getPassword()),
//				registrationDto.getAddress(), registrationDto.getPhone(),
//				Set	.asList(new Role("ROLE_USER")));
//
//		return userRepository.save(user);
//	}

	@Override
	public User saveUserForMember(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> setRoles = new HashSet<>();
		setRoles.add(roleRepository.findByRoleName("ROLE_MEMBER"));
		user.setRole(setRoles);
		return userRepository.save(user);
	}

	@Override
	public User saveUserForAdmin(AccountDto dto) {
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		user.setAddress(dto.getAddress());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		Set<Role> role  = new HashSet<>();
		role.add(roleRepository.findByRoleName(dto.getRoleName()));
		user.setRole(role);

		return userRepository.save(user);
	}

	@Override
	public User findByConfirmationToken(String confirmationToken) {
		return null;
	}

	@Override
	public User findById(long id) {
		User user = userRepository.findById(id).get();
		return user;
	}

	@Override
	public User findByDeleted(int deleted) {
		User user = userRepository.findByDeleted(deleted);
		return user;
	}

	@Override
	public Page<User> getUserByRole(Set<Role> role, int page){
		return userRepository.findByRoleIn(role, PageRequest.of(page - 1, 3));
	}

	@Override
	public List<User> getUserByRole(Set<Role> role){
		return userRepository.findByRoleIn(role);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email, 0);
	}

	@Override
	public void deleteById(long id){
		userRepository.deleteById(id);
	}

	@Override
	public void softDeleteById(long id) {
		User user = userRepository.findById(id).get();
		user.setDeleted(1);

		userRepository.save(user);
	}

	@Override
	public List<User> listBlacklistAccount(){
		return userRepository.findBlacklistAccount();
	}

	@Override
	public void restoreAccountById(long id){
		User user = userRepository.findById(id).get();
		user.setDeleted(0);

		userRepository.save(user);
	}

	@Override
	public void deleteBlacklistAccountById(long id){
		userRepository.deleteById(id);
	}

	@Override
	public void changePass(User user, String newPass){
		user.setPassword(passwordEncoder.encode(newPass));
		userRepository.save(user);
	}

	@Override
	public User updateUser(User user){
		return userRepository.save(user);
	}
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		User user = userRepository.findByEmail(username);
//		if(user == null) {
//			throw new UsernameNotFoundException("Invalid username or password.");
//		}
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//	}
//
//	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
//		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
//	}
	
}
