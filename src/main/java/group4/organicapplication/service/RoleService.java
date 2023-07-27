package group4.organicapplication.service;

import group4.organicapplication.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> findAllRole();
    Role findByRoleName(String roleName);
}
