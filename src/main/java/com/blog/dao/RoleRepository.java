package com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{

}
