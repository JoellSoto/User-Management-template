package com.springSecurity.userManagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springSecurity.userManagement.entities.Roles;

public interface RoleRepository  extends JpaRepository<Roles,Integer>{

	Optional <Roles> findByName(String string);

}
