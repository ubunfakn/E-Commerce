package com.ecom.project.ubunfakn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.project.ubunfakn.entities.Orders;

public interface OrderDao extends JpaRepository<Orders, Integer>{
    
    @Query("select o.pid from Orders o where uid=:u")
    public List<Integer> getAllProductId(@Param("u") int uid);

    @Query("select o from Orders o where o.id=:id")
    public Orders getOrdersBYId(@Param("id") int id);

    @Query("select o from Orders o where o.pid=:id")
    public Orders getOrderByProductId(@Param("id")int pid);

}
