package com.ecom.project.ubunfakn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPassword {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int otp;
    private String email;
}
