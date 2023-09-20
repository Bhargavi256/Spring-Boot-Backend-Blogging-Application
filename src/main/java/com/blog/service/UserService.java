package com.blog.service;

import java.util.List;

import com.blog.payloads.UserDTO;

public interface UserService {
	
	UserDTO registerNewUser(UserDTO user);

	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(UserDTO userDTO,Integer user_id);
	
	UserDTO getUserById(Integer user_id);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer user_id);
}
