package com.ecom.project.ubunfakn.entities;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "uid", nullable = false, columnDefinition = "int default 100") 
    int uId;
    String name;
    String phone;
    String house;
    String street;
    String district;
    String state;
    String pincode;
    String country;
}
