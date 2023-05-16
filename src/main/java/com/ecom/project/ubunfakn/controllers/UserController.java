package com.ecom.project.ubunfakn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/home")
    @ResponseBody
    public String userHome(Model model)
    {
        model.addAttribute("title", "Dashboard");
        return "Welcome guest";
    }
}
