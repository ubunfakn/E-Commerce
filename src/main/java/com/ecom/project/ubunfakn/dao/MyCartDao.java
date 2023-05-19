package com.ecom.project.ubunfakn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.project.ubunfakn.entities.MyCart;

public interface MyCartDao extends JpaRepository<MyCart, Integer>{
    
    @Query("select m.pid from MyCart m where uid=:u")
    public List<Integer> getAllProductId(@Param("u") int uid);

    @Query("select m from MyCart m where m.id=:id")
    public MyCart getCartBYId(@Param("id") int id);

    @Query("select m from MyCart m where m.pid=:id")
    public MyCart getCartByProductId(@Param("id")int pid);

}
