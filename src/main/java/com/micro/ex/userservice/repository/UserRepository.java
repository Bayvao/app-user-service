package com.micro.ex.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.ex.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
