package com.micro.ex.userservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.libraries.logging.EnableLogging;
import com.micro.ex.userservice.model.UserRequest;
import com.micro.ex.userservice.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/status/check")
	public String status() {
		return "working";
	}
	
	@EnableLogging
	@PostMapping
	public String createNewUser(@Valid @RequestBody UserRequest userDetails) {
		userService.createUser(userDetails);
		return "user created successfully";
	}
}
