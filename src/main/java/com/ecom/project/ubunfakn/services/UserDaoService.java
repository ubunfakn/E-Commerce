package com.ecom.project.ubunfakn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.project.ubunfakn.dao.UserDao;
import com.ecom.project.ubunfakn.entities.User;

@Service
public class UserDaoService {
    
    @Autowired
    UserDao userDao;

    public boolean saveUser(User user)
    {
        boolean f=false;
        try{
            this.userDao.save(user);
            f=true;
        }catch(Exception e)
        {
            e.printStackTrace();
            f=false;
        }
        return f;
    }

    public User getUserByEmail(String username)
    {
        User user=null;
        try{
            user = this.userDao.findUserByEmail(username);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUser()
    {
        List<User> users=new ArrayList<>();
        try{
            users = this.userDao.findAll();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        return users;
    }

    public User getUserByUserId(int id)
    {
        try
        {
            User user = this.userDao.findById(id);
            return user;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
