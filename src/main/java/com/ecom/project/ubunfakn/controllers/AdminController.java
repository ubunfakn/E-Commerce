package com.ecom.project.ubunfakn.controllers;

import java.io.File;
import java.nio.file.*;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.project.ubunfakn.helpers.Message;
/******************Declaration***************** */
import com.ecom.project.ubunfakn.entities.*;
import com.ecom.project.ubunfakn.services.*;

import jakarta.servlet.http.*;



@Controller
@RequestMapping("/user/admin")
public class AdminController {
    

    /******************************Declaration************************* */
    @Autowired
    UserDaoService userDaoService;

    @Autowired
    CategoriesDaoService categoriesDaoService;

    @Autowired
    ProductDaoService productDaoService;

    @Autowired
    OrdersDaoService ordersDaoService;



    /**********************************Function***************************** */
    @GetMapping("/")
    public String admin(Model model)
    {
        List<User> users= this.userDaoService.getAllUser();
        List<Categories> categories= this.categoriesDaoService.getAllCategories();
        List<Product> products= this.productDaoService.getAll();

        model.addAttribute("title", "ADMIN home");
        model.addAttribute("users", users.size());
        model.addAttribute("categories", categories.size());
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

        if(f==true) session.setAttribute("msg", new Message("success", "Category saved Successfully"));
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
        product.setCategories(categories);


        StringBuilder s=new StringBuilder(product.getPrice());

        for(int j=0;j<s.length();j++)if(s.charAt(j)==',' || s.charAt(j)==' ')s.deleteCharAt(j);

        int price = Integer.parseInt(s.toString());

        int dis=product.getMrp()-price;
        dis=dis*100;
        dis=dis/product.getMrp();

        product.setDiscount(dis);

        int quantity=(int)((Math.random()*(200-25))+25);
        product.setQuantity(quantity);
    
        boolean f=this.productDaoService.saveProduct(product);

        if(f==true) session.setAttribute("msg", new Message("success", "Product saved Successfully"));
        else session.setAttribute("msg", new Message("danger", "Something went wrong plz try again"));

        return "redirect:/user/admin/addpro";
    }


    @GetMapping("/kitchen")
    public String kitchen(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Kitchen");
        List<Product> kitchen = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Kitchen");
        model.addAttribute("product", kitchen);

        return "Admin/Products";
    }


    @GetMapping("/mobile")
    public String mobiles(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Mobile");
        List<Product> mobiles=this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Mobiles");
        model.addAttribute("product", mobiles);

        return "Admin/Products";
    }



    @GetMapping("/beauty")
    public String beauty(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Beauty");
        List<Product> beau = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Beauty");
        model.addAttribute("product", beau);

        return "Admin/Products";
    }


    @GetMapping("/fashion")
    public String fashion(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Fashion");
        List<Product> fash=this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Fashion");
        model.addAttribute("product", fash);

        return "Admin/Products";
    }


    @GetMapping("/furniture")
    public String furniture(Model model)
    {
        model.addAttribute("title", "Furniture");

        Categories categories = this.categoriesDaoService.getCatByName("Furniture");
        List<Product> furn = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", furn);

        return "Admin/Products";
    }

    @GetMapping("/computer")
    public String computers(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Computers");
        List<Product> computer = this.productDaoService.getByCatId(categories.getId());
        
        model.addAttribute("title", "Computers");
        model.addAttribute("product", computer);

        return "Admin/Products";
    }


    @GetMapping("/electronic")
    public String electronics(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Electronics");
        List<Product> electronic = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Electronics");
        model.addAttribute("product", electronic);

        return "Admin/Products";
    }


    @GetMapping("/allorders")
    public String allOrders(Model model)
    {
        List<Orders> orders = this.ordersDaoService.getAllOrders();
        List<Product> products = new ArrayList<>();

        for(int i=0;i<orders.size();i++)
        {
            Product product = this.productDaoService.getProductByProductId(orders.get(i).getPid());
            products.add(product);
        }

        model.addAttribute("title", "All Orders");
        model.addAttribute("product", products);

        return "Admin/Orders";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id")int id, HttpServletRequest request)
    {
        this.productDaoService.deleteProductByProductId(id);

        return "redirect:"+request.getHeader("Referer");
    }
}
