package com.springSecurity.userManagement.services.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSecurity.userManagement.dto.JwtAuthenticationResponse;
import com.springSecurity.userManagement.dto.RefreshTokenRequest;
import com.springSecurity.userManagement.dto.SignInRequest;
import com.springSecurity.userManagement.dto.SignUpRequest;
import com.springSecurity.userManagement.entities.Role;
import com.springSecurity.userManagement.entities.Roles;
import com.springSecurity.userManagement.entities.User;
import com.springSecurity.userManagement.repositories.RoleRepository;
import com.springSecurity.userManagement.repositories.UserRepository;
import com.springSecurity.userManagement.services.AuthenticationService;
import com.springSecurity.userManagement.services.JWTService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	private final RoleRepository roleRepository;
	
	@Autowired
	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager, JWTService jwtService, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.roleRepository = roleRepository;
	
	}
	

	public User signUp(SignUpRequest signUpRequest) {
		Roles role=new Roles();
		User user= new User();
		role=roleRepository.findByName("USER").get();
		
		
		user.setEmail(signUpRequest.getEmail());
		user.setFirstName(signUpRequest.getFirstName());
		user.setSecondName(signUpRequest.getLastName());
		user.setRoles(Collections.singletonList(role));
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		
		return userRepository.save(user);
	}
	
	public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
		var user=userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Ivalid email or password"));
		var acessToken= jwtService.generateToken(user);
		var refreshToken= jwtService.generateRefreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(acessToken);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		
		return jwtAuthenticationResponse;
	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest RefreshTokenRequest) {
		String userEmail = jwtService.extractUserName(RefreshTokenRequest.getToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if(jwtService.isTokenValid(RefreshTokenRequest.getToken(), user)) {
			var acessToken= jwtService.generateToken(user);
			
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(acessToken);
			jwtAuthenticationResponse.setRefreshToken(RefreshTokenRequest.getToken());
			return jwtAuthenticationResponse;
		}
		return null;
	}
	
}
