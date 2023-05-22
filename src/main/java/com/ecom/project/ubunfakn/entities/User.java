package com.ecom.project.ubunfakn.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String password;
    @Column(unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String address;

}
