package com.ecom.project.ubunfakn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.project.ubunfakn.entities.Product;
import com.ecom.project.ubunfakn.services.ProductDaoService;

@RestController
public class SearchController {

    @Autowired
    private ProductDaoService productDaoService;

    @GetMapping("/search/{key}")
    public ResponseEntity<?> searchHandler(@PathVariable("key") String key) {
        System.out.println(key);
        List<Product> products = this.productDaoService.findByNameKeyword(key);
        return ResponseEntity.ok(products);
    }
}
