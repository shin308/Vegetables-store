package com.myshop.service;

import com.myshop.dto.UserRegistrationDto;
import com.myshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
