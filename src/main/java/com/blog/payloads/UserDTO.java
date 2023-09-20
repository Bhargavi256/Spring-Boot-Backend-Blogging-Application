package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entity.Role;



public class UserDTO {

	
	private int id;
	
	@NotEmpty
	@Size(min=4,message="User name must be min of 4")
	private String name;
	

	@Email(message="email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min=3,max=10,message="password msut be of size 3 and 10")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<Role> rolesDtos = new HashSet<>();

	
	
	

	public Set<Role> getRolesDtos() {
		return rolesDtos;
	}

	public void setRolesDtos(Set<Role> rolesDtos) {
		this.rolesDtos = rolesDtos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	
	
	public UserDTO(int id, @NotEmpty @Size(min = 4, message = "User name must be min of 4") String name,
			@Email(message = "email address is not valid") String email,
			@NotEmpty @Size(min = 3, max = 10, message = "password msut be of size 3 and 10") String password,
			@NotEmpty String about, Set<Role> rolesDtos) {
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.rolesDtos = rolesDtos;
	}

	public UserDTO() {
		
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", rolesDtos=" + rolesDtos + "]";
	}
	
}
