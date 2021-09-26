package com.micro.ex.userservice.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.core.libraries.exceptionhandler.CustomException;
import com.micro.ex.userservice.entity.User;
import com.micro.ex.userservice.model.UserRequest;
import com.micro.ex.userservice.repository.UserRepository;
import com.micro.ex.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserRequest createUser(UserRequest userDetails) {
		User user = new User();
		if (userRepository.findByUserId(userDetails.getEmail()) != null) {
			throw new CustomException(503, "User id already exists");
		}
		user.setUserId(userDetails.getEmail());
		user.setEmailId(userDetails.getEmail());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

		userRepository.save(user);

		return userDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserId(username);
		if (user == null) {
			throw new BadCredentialsException("User not found with email");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(), true, true,
				true, true, new ArrayList<>());
	}

	@Override
	public UserRequest loadUserDetailsByEmail(String userName) {
		User user = userRepository.findByUserId(userName);
		if (user == null) {
			throw new BadCredentialsException("User not found with email");
		}
		
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail(user.getUserId());
		userRequest.setFirstName(user.getFirstName());
		userRequest.setLastName(user.getLastName());
		
		return userRequest;
	}

}
