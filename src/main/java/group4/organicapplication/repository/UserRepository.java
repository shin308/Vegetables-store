package group4.organicapplication.repository;

import group4.organicapplication.model.Category;
import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM User u WHERE u.email = ?1 AND u.deleted = 0")
	User findByEmail(String email, int deleted);

	User findByDeleted(int deleted);

	@Query("SELECT u FROM User u JOIN u.role r WHERE r IN :roles AND u.deleted = 0")
	Page<User> findByRoleIn(@Param("roles") Set<Role> roles, Pageable of);

	List<User> findByRoleIn(Set<Role> role);

	@Query("SELECT u FROM User u WHERE u.deleted = 1")
	List<User> findBlacklistAccount();
}
