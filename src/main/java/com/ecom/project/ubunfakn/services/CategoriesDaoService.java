package com.ecom.project.ubunfakn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.project.ubunfakn.dao.CategoriesDao;
import com.ecom.project.ubunfakn.entities.Categories;

@Service
public class CategoriesDaoService {
    
    @Autowired
    CategoriesDao categoriesDao;

    public List<Categories> getAllCategories()
    {
        List<Categories> categories=new ArrayList<>();
        try
        {
            categories=this.categoriesDao.findAll();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return categories;
    }

    public boolean saveCategory(Categories categories)
    {
        boolean f=false;
        try {
            this.categoriesDao.save(categories);
            System.out.println("Category saved Successfully");
            f=true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public boolean deleteAllCat()
    {
        boolean f=false;
        try {
            this.categoriesDao.deleteAll();
            f=true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return f;
    }

    public Categories getCatByName(String name)
    {
        try {
            Categories categories = this.categoriesDao.getByName(name);
            return categories;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
