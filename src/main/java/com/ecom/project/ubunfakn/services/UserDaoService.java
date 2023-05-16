package com.ecom.project.ubunfakn.services;

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
}