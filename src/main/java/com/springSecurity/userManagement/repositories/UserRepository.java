package com.springSecurity.userManagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springSecurity.userManagement.entities.Role;
import com.springSecurity.userManagement.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	Optional<User> findByEmail(String email);
	
}
