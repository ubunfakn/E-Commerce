package com.ecom.project.ubunfakn.entities;

import org.hibernate.validator.constraints.Normalized;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int pid;
    private int uid;

    private int price;

}
