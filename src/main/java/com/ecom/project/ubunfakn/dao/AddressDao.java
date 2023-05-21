package com.ecom.project.ubunfakn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.project.ubunfakn.entities.Address;

public interface AddressDao extends JpaRepository<Address,Integer> {
    
    public List<Address> findByuId(int uid);

    public Address findById(int id);
}
