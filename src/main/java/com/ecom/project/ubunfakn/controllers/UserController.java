package com.ecom.project.ubunfakn.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecom.project.ubunfakn.entities.User;
import com.ecom.project.ubunfakn.services.UserDaoService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserDaoService userDaoService;

    @GetMapping("/")
    @ResponseBody
    public String userHome(Model model, Principal principal)
    {
        return "home";
    }
}
