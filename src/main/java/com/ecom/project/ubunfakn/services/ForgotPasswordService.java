package com.ecom.project.ubunfakn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.project.ubunfakn.dao.ForgotPasswordDao;
import com.ecom.project.ubunfakn.entities.ForgotPassword;

@Service
public class ForgotPasswordService {
    
    @Autowired
    ForgotPasswordDao forgotPasswordDao;

    public boolean deleteAllFromForgotPassword()
    {
        boolean f=false;
        try{
            
            this.forgotPasswordDao.deleteAll();
            f=true;
        }catch(Exception e)
        {
            e.printStackTrace();
            f=false;
        }
        return f;
    }

    public boolean saveAll(ForgotPassword forgotPassword)
    {
        boolean f=false;
        try{
            
            this.forgotPasswordDao.save(forgotPassword);
            f=true;
        }catch(Exception e)
        {
            e.printStackTrace();
            f=false;
        }
        return f;
    }

    public List<ForgotPassword> getAll()
    {
        List<ForgotPassword> list=null;
        try{
            
            list= this.forgotPasswordDao.findAll();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }
}
