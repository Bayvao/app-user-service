package com.micro.ex.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.micro.ex.userservice.entity.User;
import com.micro.ex.userservice.model.UserRequest;
import com.micro.ex.userservice.repository.UserRepository;
import com.micro.ex.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserRequest createUser(UserRequest userDetails) {
		User user = new User();
		user.setUserId(userDetails.getEmail());
		user.setEmailId(userDetails.getEmail());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		userRepository.save(user);
		
		return userDetails;
	}

}
