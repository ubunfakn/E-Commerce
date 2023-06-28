package com.ecom.project.ubunfakn.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecom.project.ubunfakn.entities.User;


public class CustomeUserDetailService implements UserDetails{

    /******************Declaration***************** */
    private User user;


    /******************Constructor***************** */
    CustomeUserDetailService(User user)
    {
        this.user=user;
    }


    /******************Function***************** */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority=null;
        try
        {
            simpleGrantedAuthority=new SimpleGrantedAuthority(user.getRole().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return List.of(simpleGrantedAuthority);
    }


    @Override
    public String getPassword() {
        try
        {
            return this.user.getPassword();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }



    @Override
    public String getUsername() {
        try
        {
            return this.user.getEmail();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    
    @Override
    public boolean isEnabled() {
        return true;
    }

}
