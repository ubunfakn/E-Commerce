package com.ecom.project.ubunfakn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.project.ubunfakn.dao.MyCartDao;
import com.ecom.project.ubunfakn.entities.MyCart;

@Service
public class MyCartDaoService {
    
    @Autowired
    MyCartDao myCartDao;

    public boolean savetoMyCart(MyCart myCart)
    {
        boolean f=false;
        try
        {
            this.myCartDao.save(myCart);
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
            pids = this.myCartDao.getAllProductId(uid);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return pids;
    }

    public boolean deleteFromMyCart(MyCart myCart)
    {
        boolean f=false;
        try
        {
            this.myCartDao.delete(myCart);
            f=true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return f;
    }

    public MyCart getCartById(int id)
    {
        MyCart myCart2 = new MyCart();
        try
        {
            myCart2 = this.myCartDao.getCartBYId(id);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return myCart2;
    }

    public MyCart getCartByProductId(int pid)
    {
        MyCart myCart2 = new MyCart();
        try
        {
            myCart2 = this.myCartDao.getCartByProductId(pid);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return myCart2;
    }

}
