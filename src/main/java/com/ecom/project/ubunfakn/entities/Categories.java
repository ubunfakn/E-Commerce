package com.ecom.project.ubunfakn.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categories {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "categories" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> product;
}