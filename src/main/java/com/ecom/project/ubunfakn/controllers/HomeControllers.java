package com.ecom.project.ubunfakn.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecom.project.ubunfakn.entities.*;
import com.ecom.project.ubunfakn.helpers.Message;
import com.ecom.project.ubunfakn.services.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeControllers {
    
    @Autowired
    UserDaoService userDaoService;

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @Autowired
    EMailService eMailService;

    @Autowired
    ProductDaoService productDaoService;

    @Autowired
    CategoriesDaoService categoriesDaoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("title", "Home");
        List<String> cat=this.productDaoService.getAllCategory();
        model.addAttribute("category", cat);

        List<String> brand=this.productDaoService.getAllBrand();
        Collections.sort(brand);
        model.addAttribute("brand", brand);

        List<String> name=this.productDaoService.getAllNames();
        Collections.sort(name);
        List<String> newList=name.subList(0, 10);
        model.addAttribute("name", newList);

        List<Product> budgeList=this.productDaoService.budgetProducts();
        model.addAttribute("budget", budgeList);
        return "home";
    }

    @GetMapping("/reg")
    public String registerForm(Model model)
    {
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/submit-form")
    public String handleForm(@ModelAttribute User user, @RequestParam("password") String password, Model model, HttpSession session)
    {
            user.setPassword(this.passwordEncoder.encode(password));
            user.setRole(Role.valueOf(Role.ROLE_USER.toString()));
            boolean f=userDaoService.saveUser(user);
            if(f==true)
            {
                session.setAttribute("success", "User Registration Successfull");
                return "redirect:/login";
            }
            
            session.setAttribute("msg", new Message("danger", "Something went wrong!! Please try again"));
            return "register";
    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session)
    {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/forgot")
    public String forgotPasswordForm(Model model, HttpSession session)
    {
        model.addAttribute("title", "Forgot Password");
        return "forgot_password";
    }

    @PostMapping("/generateOTP")
    public String generateOTP(@RequestParam("email")String email ,Model model, HttpSession session)
    {
        model.addAttribute("title", "Verify OTP");

        User user=this.userDaoService.getUserByEmail(email);

        if(user==null)
        {
            session.setAttribute("msg", new Message("danger","E-mail is not registered with us!!"));
            return "redirect:/forgot";
        }
        int otp=(int)((Math.random()*(9999-1000))+1000);
        ForgotPassword forgotPassword=new ForgotPassword();

        forgotPassword.setEmail(email);
        forgotPassword.setOtp(otp);
        try
        {
            this.forgotPasswordService.deleteAllFromForgotPassword();
            this.forgotPasswordService.saveAll(forgotPassword);
            this.eMailService.sendEmail(String.valueOf(otp), "Forgot Password otp verification", email, "ankitnashine12@gmail.com");

        }catch(Exception e)
        {
            e.printStackTrace();
            session.setAttribute("msg", new Message("danger", "Something went wrong!! Please try again"));
            return "redirect:/forgot";
        }

        session.setAttribute("msg", new Message("success", "OTP send to your registered E-mail"));
        return "verify_OTP";
    }

    @PostMapping("/verifyOTP")
    public String verifyOTP(@RequestParam("otp")String otp ,Model model, HttpSession session)
    {
        List<ForgotPassword> list=this.forgotPasswordService.getAll();
        model.addAttribute("title", "Change-Password");
        if(Integer.parseInt(otp)==list.get(0).getOtp())
        {
            
            return "change_password";
        }
        else
        {
            session.setAttribute("msg", new Message("danger", "Please Enter correct OTP"));
            return "redirect:/forgot";
        }
    }

    @PostMapping("/changepasswordsubmit")
    public String changePassword(@RequestParam("password")String password, @RequestParam("verifypassword")String verifyPassword, HttpSession session)
    {
        try
        {
            List<ForgotPassword> list=this.forgotPasswordService.getAll();
            User user = this.userDaoService.getUserByEmail(list.get(0).getEmail());
            user.setPassword(this.passwordEncoder.encode(password));
            this.userDaoService.saveUser(user);
            session.setAttribute("msg", new Message("success", "Password Changed Successfully"));
            return "redirect:/login";
        }catch(Exception e)
        {
            session.setAttribute("msg", new Message("danger", "Something went wrong!! Please try again"));
            return "change_password";
        }
    }

    @GetMapping("/mobile")
    public String mobiles(Model model)
    {
        model.addAttribute("title", "Mobiles");

        Categories categories = this.categoriesDaoService.getCatByName("Mobile");
        List<Product> mobiles=this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", mobiles);
        return "products/Products";
    }

    @GetMapping("/beauty")
    public String beauty(Model model)
    {
        model.addAttribute("title", "Beauty");

        Categories categories = this.categoriesDaoService.getCatByName("Beauty");
        List<Product> beau = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", beau);

        return "products/Products";
    }

    @GetMapping("/fashion")
    public String fashion(Model model)
    {
        model.addAttribute("title", "Fashion");

        Categories categories = this.categoriesDaoService.getCatByName("Fashion");
        List<Product> fash=this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", fash);

        return "products/Products";
    }
    @GetMapping("/furniture")
    public String furniture(Model model)
    {
        model.addAttribute("title", "Furniture");

        Categories categories = this.categoriesDaoService.getCatByName("Furniture");
        List<Product> furn = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", furn);

        return "products/Products";
    }

    @GetMapping("/computer")
    public String computers(Model model)
    {
        model.addAttribute("title", "Computers");

        Categories categories = this.categoriesDaoService.getCatByName("Computers");
        List<Product> computer = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", computer);

        return "products/Products";
    }

    @GetMapping("/electronic")
    public String electronics(Model model)
    {
        model.addAttribute("title", "Electronics");

        Categories categories = this.categoriesDaoService.getCatByName("Electronics");
        List<Product> electronic = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", electronic);

        return "products/Products";
    }

    // @GetMapping("/toys")
    // public String toys(Model model)
    // {
    //     model.addAttribute("title", "Toys");
    //     List<Product> toys=this.productDaoService.getByCat("Toys");
    //     model.addAttribute("product", toys);
    //     return "products/Products";
    // }

    @GetMapping("/kitchen")
    public String kitchen(Model model)
    {
        model.addAttribute("title", "Kitchen");

        Categories categories = this.categoriesDaoService.getCatByName("Kitchen");
        List<Product> kitchen = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", kitchen);

        return "products/Products";
    }

    // @GetMapping("/add")
    // @ResponseBody
    // public String add()
    // {
    //     Product product=new Product();
    //     product.setBrand("POND'S");
    //     product.setCategory("Beauty");
    //     product.setDescription(" Non-Oily, Mattifying Daily Face Moisturizer - With Niacinamide to Lighten Dark Spots for Glowing Skin");
    //     product.setName("POND'S Bright Beauty SPF 15 Day Cream 50 g");
    //     product.setPrice("209");
    //     product.setImage("Ponds.jpg");
    //     this.productDaoService.saveProduct(product);

    //     return "successfully added";
    // }

    @GetMapping("/{id}/product/{name}")
    public String productOpen(@PathVariable("id")int id, @PathVariable("name")String name)
    {
        Product product=this.productDaoService.getProductByProductId(id);
        return "fetched";
    }

    @GetMapping("/cat")
    @ResponseBody
    public String catAdd()
    {

        return "done";
    }
}
