package com.blog.controller;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exception.ApiException;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDTO;
import com.blog.security.JwtTokenHelper;
import com.blog.service.UserService;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request) throws Exception{
	//	System.out.println("hmmm");
		authenticate(request.getUsername(),request.getPassword());
	//	System.out.println("hi");
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
	//	System.out.println("hello");
		 String token = jwtTokenHelper.generateToken(userDetails);
	//	System.out.println("how are you");
		JwtAuthResponse response = new JwtAuthResponse();
	//	System.out.println("jijiji");
		response.setToken(token);
	//	System.out.println("kokok");
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
	//	System.out.println("1");
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
	//	System.out.println("2");
		try {
	//		System.out.println("3");
		authenticationManager.authenticate(authenticationToken);
	//	System.out.println("4");
		}catch (BadCredentialsException e) {
			e.printStackTrace();
             System.out.println("Invalid Credentials");
             throw new ApiException("Invalid username or password");
             
		}
	}
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDto){
		UserDTO registeredUser = userService.registerNewUser(userDto);
		System.out.println(registeredUser);
		return new ResponseEntity<UserDTO>(registeredUser,HttpStatus.CREATED);
	}
}
