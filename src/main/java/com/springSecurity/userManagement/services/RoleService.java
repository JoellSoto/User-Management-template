package com.springSecurity.userManagement.services;

import java.util.List;

import com.springSecurity.userManagement.entities.Role;
import com.springSecurity.userManagement.entities.Roles;
import com.springSecurity.userManagement.entities.User;

public interface RoleService {

	List<Roles> getAllRoles();
	
	Roles removeAllUserFromRole(int roleId);
	
	User removeUserFromRole(int userId,int roleId);
	
	User assignUserToRole(int userId,int roleId);
	
}
