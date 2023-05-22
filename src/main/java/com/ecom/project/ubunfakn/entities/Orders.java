package com.ecom.project.ubunfakn.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int pid;
    private int uid;
    int productMrp;

    private String price;
    private String paymentStatus;

}
