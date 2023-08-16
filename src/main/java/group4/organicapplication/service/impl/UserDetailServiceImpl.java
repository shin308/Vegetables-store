package group4.organicapplication.service.impl;

import group4.organicapplication.model.Role;
import group4.organicapplication.model.User;
import group4.organicapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Primary
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(username, 0);
        if(user == null){
            throw new UsernameNotFoundException("User with mail "+ username + " was not be found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Role> roles = user.getRole();
        for (Role role : roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);


    }
}
