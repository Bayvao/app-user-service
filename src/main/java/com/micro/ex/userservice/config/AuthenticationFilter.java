package com.micro.ex.userservice.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.core.libraries.exceptionhandler.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.ex.userservice.model.LoginRequest;
import com.micro.ex.userservice.model.UserRequest;
import com.micro.ex.userservice.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userService;

	@Autowired
	private Environment environment;

	public AuthenticationFilter(UserService userService, Environment environment, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new CustomException(401, "Username or Password is invalid! ");
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String userName = ((User) auth.getPrincipal()).getUsername();

		UserRequest userDetails = userService.loadUserDetailsByEmail(userName);

		SecretKey key = Keys.hmacShaKeyFor(environment.getRequiredProperty("token.secret").getBytes());
		String token = Jwts.builder().setSubject(userDetails.getEmail())
				.setExpiration(new Date(System.currentTimeMillis()
						+ Long.parseLong(environment.getRequiredProperty("token.expiration.time"))))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getEmail());
	}

}
