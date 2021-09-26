package com.micro.ex.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.micro.ex.userservice.model.UserRequest;

public interface UserService extends UserDetailsService{

	public UserRequest createUser(UserRequest userDetails);

	public UserRequest loadUserDetailsByEmail(String userName);
}
