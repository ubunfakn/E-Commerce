package com.ecom.project.ubunfakn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.project.ubunfakn.entities.ForgotPassword;

public interface ForgotPasswordDao extends JpaRepository<ForgotPassword, Integer>{
    
}
