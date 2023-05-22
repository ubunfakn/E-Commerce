package com.ecom.project.ubunfakn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.project.ubunfakn.dao.OrderDao;
import com.ecom.project.ubunfakn.entities.Orders;

@Service
public class OrdersDaoService {
    
    @Autowired
    OrderDao orderDao;

    public boolean savetoOrders(Orders orders)
    {
        boolean f=false;
        try
        {
            this.orderDao.save(orders);
            f=true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return f;
    }

    public List<Integer> getAllProductId(int uid)
    {
        List<Integer> pids= new ArrayList<>();
        try
        {
            pids = this.orderDao.getAllProductId(uid);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return pids;
    }

    public Orders getOrderById(int id)
    {
        Orders orders = new Orders();
        try
        {
            orders = this.orderDao.getOrdersBYId(id);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return orders;
    }

    public Orders getOrderByProductId(int pid)
    {
        Orders orders = new Orders();
        try
        {
        orders = this.orderDao.getOrderByProductId(pid);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Orders> getAllOrders()
    {
        List<Orders> orders = this.orderDao.findAll();
        return orders;
    }

}
