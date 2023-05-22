package com.ecom.project.ubunfakn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.project.ubunfakn.entities.User;

public interface UserDao extends JpaRepository<User,Integer>{
    
    public User findUserByEmail(String email);

    public User findById(int id);
}
