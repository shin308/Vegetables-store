package group4.organicapplication.service.impl;

import group4.organicapplication.model.Role;
import group4.organicapplication.repository.RoleRepository;
import group4.organicapplication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAllRole(){
        return roleRepository.findAll();
    }

    @Override
    public Role findByRoleName(String roleName){
        return roleRepository.findByRoleName(roleName);
    }
}
