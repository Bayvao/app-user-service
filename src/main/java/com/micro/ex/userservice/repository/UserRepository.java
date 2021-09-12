package com.micro.ex.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.ex.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUserId(String email);


}
