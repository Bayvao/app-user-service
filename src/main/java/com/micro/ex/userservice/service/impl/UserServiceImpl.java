package com.micro.ex.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.micro.ex.userservice.entity.User;
import com.micro.ex.userservice.model.UserRequest;
import com.micro.ex.userservice.repository.UserRepository;
import com.micro.ex.userservice.service.UserService;

public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void createUser(UserRequest userDetails) {
		User user = new User();
		user.setUserId(userDetails.getEmail());
		user.setEmailId(userDetails.getEmail());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPassword(null);
		
		userRepository.save(user);
		
	}

}
