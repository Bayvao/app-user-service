package com.micro.ex.userservice.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.ex.userservice.model.UserRequest;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@GetMapping("/status/check")
	public String status() {
		return "working";
	}
	
	@PostMapping
	public String createNewUser(@Valid @RequestBody UserRequest userDetails) {
		
		return "user created successfully";
	}
}
