package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDTO;
import com.blog.service.UserService;







@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDto){
		UserDTO createUser = userService.createUser(userDto);
		return new ResponseEntity<>(createUser,HttpStatus.CREATED);
	}
	
	@PutMapping("/{user_id}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto,@PathVariable("user_id") Integer uid){
		UserDTO updateUser = userService.updateUser(userDto, uid);
		return  ResponseEntity.ok(updateUser);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{user_id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("user_id")Integer uid){
		
		userService.deleteUser(uid);
		return new ResponseEntity(new ApiResponse("user is deleted",true),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{user_id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("user_id")Integer uid){
		return ResponseEntity.ok(userService.getUserById(uid));
	}
}
