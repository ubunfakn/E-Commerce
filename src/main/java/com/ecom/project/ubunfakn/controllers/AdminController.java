package com.ecom.project.ubunfakn.controllers;

import java.io.File;
import java.nio.file.*;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.project.ubunfakn.helpers.Message;

import com.ecom.project.ubunfakn.entities.*;
import com.ecom.project.ubunfakn.services.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/admin")
public class AdminController {
    
    @Autowired
    UserDaoService userDaoService;

    @Autowired
    CategoriesDaoService categoriesDaoService;

    @Autowired
    ProductDaoService productDaoService;

    @GetMapping("/")
    public String admin(Model model)
    {
        model.addAttribute("title", "ADMIN home");

        List<User> users= this.userDaoService.getAllUser();
        model.addAttribute("users", users.size());

        List<Categories> categories= this.categoriesDaoService.getAllCategories();
        model.addAttribute("categories", categories.size());

        List<Product> products= this.productDaoService.getAll();
        model.addAttribute("products", products.size());

        return "Admin/ADMIN Home";
    }

    @GetMapping("/addcat")
    public String addCategoryForm(Model model)
    {
        model.addAttribute("title", "Add Category");
        return "Admin/add-category";
    }

    @PostMapping("/submit-cat")
    public String submitCategory(@ModelAttribute Categories categories, HttpSession session)
    {
        boolean f=this.categoriesDaoService.saveCategory(categories);
        if(f==true)
        session.setAttribute("msg", new Message("success", "Category saved Successfully"));
        else session.setAttribute("msg", new Message("danger", "Something went wrong plz try again"));
        return "Admin/add-category";
    }

    @GetMapping("/addpro")
    public String addProductForm(Model model)
    {
        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", this.categoriesDaoService.getAllCategories());
        return "Admin/add-product";
    }

    @PostMapping("/submit-pro")
    public String submitProduct(@ModelAttribute Product product,@RequestParam("categories.name")String name ,@RequestParam("pro-image")MultipartFile file, HttpSession session)throws Exception
    {
        //Processing and uploading file
        if(!file.isEmpty())
        {
            product.setImage(file.getOriginalFilename());
            File fileinput = new ClassPathResource("static/images").getFile();

            Path path=Paths.get(fileinput.getAbsolutePath()+File.separator+file.getOriginalFilename());
            Files.copy(file.getInputStream(), path , StandardCopyOption.REPLACE_EXISTING);
        }

        Categories categories = this.categoriesDaoService.getCatByName(name);
        // System.out.println(categories);
        product.setCategories(categories);

        boolean f=this.productDaoService.saveProduct(product);

        if(f==true)
        session.setAttribute("msg", new Message("success", "Product saved Successfully"));
        else session.setAttribute("msg", new Message("danger", "Something went wrong plz try again"));
        return "redirect:/user/admin/addpro";
    }

    // @GetMapping("/del")
    // @ResponseBody
    // public String delete()
    // {
    //     boolean f=this.categoriesDaoService.deleteAllCat();
    //     if(f==true)
    //     return "deleted";

    //     else return "ohhh no";
    // }

    // @GetMapping("/edit")
    // @ResponseBody
    // public String edit()
    // {
    //     Categories categories = this.categoriesDaoService.getCatByName("Beauty");

    //     List<Product> products = this.productDaoService.getByCat(categories.getName());
    //     for(int i=0;i<products.size();i++)
    //     {
    //         products.get(i).setCategories(categories);
    //         this.productDaoService.saveProduct(products.get(i));
    //     }

    //     return "updated";
    // }
}
