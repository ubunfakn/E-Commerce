package com.ecom.project.ubunfakn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

import com.ecom.project.ubunfakn.entities.User;
import com.ecom.project.ubunfakn.services.UserDaoService;

public class UserDetailService implements UserDetailsService{


    /************************Declaration********************** */
    @Autowired
    UserDaoService userDaoService;

    
    /****************************Function*************************** */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=null;
        try {
            
            user = this.userDaoService.getUserByEmail(username);
            if(user==null) throw new UsernameNotFoundException("Invalid UserName");

        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDetails userDetails = new CustomeUserDetailService(user);
        return userDetails;
    }

}
