package com.shankar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shankar.request.AutheticteRequest;
import com.shankar.service.MyUserDetailsService;
import com.shankar.util.JwtUtil;

@RestController
public class AutheticateRestController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public String autheicateUser(@RequestBody AutheticteRequest request) throws Exception {
		try {

		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		} catch (Exception e) {
			throw new Exception("Invalid Credentials");
		}
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());

		String token = jwtUtil.generateToken(userDetails);

		return token;
	}

}
