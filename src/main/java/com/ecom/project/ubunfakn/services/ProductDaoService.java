package com.ecom.project.ubunfakn.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.project.ubunfakn.dao.ProductDao;
import com.ecom.project.ubunfakn.entities.Product;

@Service
public class ProductDaoService {

    @Autowired
    ProductDao productDao;

    public void saveProduct(Product product)
    {
        try {

            this.productDao.save(product);
            System.out.println("Product saved Successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getByCat(String category)
    {
       try
       {
        List<Product> list = this.productDao.findByCategory(category);
        return list;
       }
       catch(Exception e)
       {
        return null;
       }
    }

    public List<Product> getAll()
    {
        try{
            List<Product> list = this.productDao.findAll();
            return list;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public List<String> getAllCategory()
    {
        try{
            List<String> list=this.productDao.getAllCategory();
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllBrand()
    {
        try{
            List<String> list=this.productDao.getAllBrand();
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllNames()
    {
        try{
            List<String> list=this.productDao.getAllNames();
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> budgetProducts()
    {
        try{
            List<Product> list=this.productDao.budget();
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Product getProductByProductId(int id)
    {
        try{
            Product product=this.productDao.getByProductId(id);
            return product;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}