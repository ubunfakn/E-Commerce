package com.ecom.project.ubunfakn.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;
    private String image;
    private String category;
    private String brand;
    private String price;
    private int quantity;
    private int discount;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categories categories;

}
