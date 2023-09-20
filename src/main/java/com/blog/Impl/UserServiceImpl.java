package com.blog.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.Constants;
import com.blog.dao.RoleRepository;
import com.blog.dao.UserRepository;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.UserDTO;
import com.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = dtoToUser(userDTO);
		User save = this.userRepository.save(user);
		return userToDto(save);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer user_id) {
		User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", user_id));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        
        User save = userRepository.save(user);
        
		return userToDto(save);
	}

	@Override
	public UserDTO getUserById(Integer user_id) {
		User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", user_id));
		
		return userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
	
		
		 List<User> users = this.userRepository.findAll();
		List<UserDTO> userDtos = users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer user_id) {
		
		User user = userRepository.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","Id",user_id));
        userRepository.delete(user);
	}

	private User dtoToUser(UserDTO userDto) {
		User user = modelMapper.map(userDto,User.class);
		//BeanUtils.copyProperties(userDto, user);

		return user;
	}

	private UserDTO userToDto(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		//BeanUtils.copyProperties(user, userDto);

		return userDto;
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDto) {
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role = roleRepository.findById(Constants.NORMAL_USER).get();
		
		User user2 = userRepository.save(user);
		user2.getRoles().forEach(r->{
			System.out.println(r.getName()+" user");
		});
		//UserDTO userDTO2 = ;
//		userDTO2.getRolesDtos().forEach(r->{
//			System.out.println(r.getName()+" DTO");
//		});
		return modelMapper.map(user2, UserDTO.class);
	}

}
