package com.ecom.project.ubunfakn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.project.ubunfakn.entities.Categories;

public interface CategoriesDao extends JpaRepository<Categories, Integer>{
    
    @Query("select c from Categories c where c.name=:nam")
    public Categories getByName(@Param("nam")String name);
}
