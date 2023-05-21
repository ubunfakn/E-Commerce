package com.ecom.project.ubunfakn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.project.ubunfakn.dao.AddressDao;
import com.ecom.project.ubunfakn.entities.Address;

@Service
public class AddressDaoService {
    
    @Autowired
    AddressDao addressDao;

    public List<Address> getAllAddressByUserId(int uid)
    {
        List<Address> addresses = new ArrayList<>();
        try
        {
            addresses = this.addressDao.findByuId(uid);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return addresses;
    }

    public boolean saveAddress(Address address)
    {
        boolean f = false;
        try
        {
            this.addressDao.save(address);
            f=true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return f;
    }

    public Address getById(int id)
    {
        Address address = this.addressDao.findById(id);
        return address;
    }
}
