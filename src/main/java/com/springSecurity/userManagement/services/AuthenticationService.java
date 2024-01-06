package com.springSecurity.userManagement.services;

import com.springSecurity.userManagement.dto.JwtAuthenticationResponse;
import com.springSecurity.userManagement.dto.RefreshTokenRequest;
import com.springSecurity.userManagement.dto.SignInRequest;
import com.springSecurity.userManagement.dto.SignUpRequest;
import com.springSecurity.userManagement.entities.User;

public interface AuthenticationService {
 
	public User signUp(SignUpRequest signUpRequest);
	public JwtAuthenticationResponse signIn(SignInRequest signInRequest);
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest RefreshTokenRequest);
}
