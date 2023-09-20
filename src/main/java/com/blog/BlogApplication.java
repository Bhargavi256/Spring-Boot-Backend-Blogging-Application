package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.Constants;
import com.blog.dao.RoleRepository;
import com.blog.entity.Role;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
@EnableSwagger2
@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("abc"));
		try {
			Role role = new Role();
            role.setId(Constants.ADMIN_USER);
            role.setName("ROLE_ADMIN");
            
            Role role1 = new Role();
            role1.setId(Constants.NORMAL_USER);
            role1.setName("ROLE_NORMAL");
            List<Role> list = List.of(role,role1);
            List<Role> all = roleRepository.saveAll(list);
            all.forEach(r->{
            	System.out.println(r.getName());
            });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
