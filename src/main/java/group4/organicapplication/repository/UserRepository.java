package group4.organicapplication.repository;

import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

	Page<User> findByRoleIn(Set<Role> role, Pageable of);

	List<User> findByRoleIn(Set<Role> role);
}
