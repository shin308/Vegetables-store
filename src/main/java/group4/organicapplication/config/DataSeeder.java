package group4.organicapplication.config;

import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import group4.organicapplication.repository.RoleRepository;
import group4.organicapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0){
        if(roleRepository.findByRoleName("ROLE_ADMIN") == null){
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        if(roleRepository.findByRoleName("ROLE_MEMBER") == null){
            roleRepository.save(new Role("ROLE_MEMBER"));
        }

        if (roleRepository.findByRoleName("ROLE_SHIPPER") == null) {
            roleRepository.save(new Role("ROLE_SHIPPER"));
        }

        // Admin account
        if (userRepository.findByEmail("admin@gmail.com", 0) == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setFirstName("Quoc Nhat");
            admin.setLastName("Vo");
            admin.setPhone("123456789");
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("ROLE_ADMIN"));
            roles.add(roleRepository.findByRoleName("ROLE_MEMBER"));
            admin.setRole(roles);
            userRepository.save(admin);
        }

        // Member account
        if (userRepository.findByEmail("member@gmail.com", 0) == null) {
            User member = new User();
            member.setEmail("member@gmail.com");
            member.setPassword(passwordEncoder.encode("123456"));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("ROLE_MEMBER"));
            member.setRole(roles);
            userRepository.save(member);
        }

        // Shipper account
        if (userRepository.findByEmail("shipper@gmail.com", 0) == null) {
            User member = new User();
            member.setEmail("shipper@gmail.com");
            member.setPassword(passwordEncoder.encode("123456"));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("ROLE_SHIPPER"));
            member.setRole(roles);
            userRepository.save(member);
        }
    }
}
