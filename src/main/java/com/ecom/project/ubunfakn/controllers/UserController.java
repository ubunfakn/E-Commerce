package com.ecom.project.ubunfakn.controllers;

import java.security.Principal;
import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecom.project.ubunfakn.entities.*;

import com.ecom.project.ubunfakn.services.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

import com.ecom.project.ubunfakn.helpers.Message;;

@Controller
@RequestMapping("/user")
public class UserController {
    
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
    MyCartDaoService myCartDaoService;

    @Autowired
    AddressDaoService addressDaoService;



    @GetMapping("/mobile")
    public String mobiles(Model model)
    {
        model.addAttribute("title", "Mobiles");

        Categories categories = this.categoriesDaoService.getCatByName("Mobile");
        List<Product> mobiles=this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", mobiles);
        return "Normal/Products";
    }

    @GetMapping("/kitchen")
    public String kitchen(Model model)
    {
        model.addAttribute("title", "Kitchen");

        Categories categories = this.categoriesDaoService.getCatByName("Kitchen");
        List<Product> kitchen = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", kitchen);

        return "Normal/Products";
    }

    @GetMapping("/beauty")
    public String beauty(Model model)
    {
        model.addAttribute("title", "Beauty");

        Categories categories = this.categoriesDaoService.getCatByName("Beauty");
        List<Product> beau = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", beau);

        return "Normal/Products";
    }

    @GetMapping("/fashion")
    public String fashion(Model model)
    {
        model.addAttribute("title", "Fashion");

        Categories categories = this.categoriesDaoService.getCatByName("Fashion");
        List<Product> fash=this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", fash);

        return "Normal/Products";
    }
    @GetMapping("/furniture")
    public String furniture(Model model)
    {
        model.addAttribute("title", "Furniture");

        Categories categories = this.categoriesDaoService.getCatByName("Furniture");
        List<Product> furn = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", furn);

        return "Normal/Products";
    }

    @GetMapping("/computer")
    public String computers(Model model)
    {
        model.addAttribute("title", "Computers");

        Categories categories = this.categoriesDaoService.getCatByName("Computers");
        List<Product> computer = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", computer);

        return "Normal/Products";
    }

    @GetMapping("/electronic")
    public String electronics(Model model)
    {
        model.addAttribute("title", "Electronics");

        Categories categories = this.categoriesDaoService.getCatByName("Electronics");
        List<Product> electronic = this.productDaoService.getByCatId(categories.getId());
        model.addAttribute("product", electronic);

        return "Normal/Products";
    }

    @GetMapping("/")
    public String userHome(Model model)
    {
        List<Product> products = this.productDaoService.getAllByDiscount(30);
        model.addAttribute("product", products.subList(0, 4));

        List<Product> pro = this.productDaoService.getAllByExactDiscount(60);
        model.addAttribute("pro", pro.subList(0, 4));

        List<Product> mobiles = this.productDaoService.getByCat("Mobile");
        model.addAttribute("mobile", mobiles.subList(0, 4));

        List<Product> elect = this.productDaoService.getByCat("Electronics");
        model.addAttribute("elec", elect.subList(0, 4));

        List<Product> comp = this.productDaoService.getByCat("Computers");
        model.addAttribute("comp", comp.subList(0, 4));

        model.addAttribute("title", "User-Home");
        return "Normal/home";
    }

    @GetMapping("/cart")
    public String cartOpen(Model model, Principal principal)
    {
        model.addAttribute("title", "My-Cart");
        List<Integer> pids = this.myCartDaoService.getAllProductId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        List<Product> products=new ArrayList<>();
        System.out.println(pids);
        long sum=0;
        for(int i=0;i<pids.size();i++)
        {
            Product product = this.productDaoService.getProductByProductId(pids.get(i));
            // System.out.println(product);
            products.add(product);
        }
        List<MyCart> myCarts = this.myCartDaoService.getAllCart();
        for(int i=0;i<myCarts.size();i++)
        {
            sum+=myCarts.get(i).getPrice();
        }
        
        model.addAttribute("sum", sum);
        model.addAttribute("product", products);
        return "Normal/MyCart";
    }

    @GetMapping("/{id}/cart/{name}")
    public String addToCart(@PathVariable("id")int id, Model model, Principal principal, HttpSession session)
    {
        
        List<Integer> pids = this.myCartDaoService.getAllProductId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        if(pids.contains((int)id))return "redirect:/user/cart";

        Product product = this.productDaoService.getProductByProductId(id);

        MyCart myCart = new MyCart();

        StringBuilder s=new StringBuilder(product.getPrice());
        for(int i=0;i<s.length();i++)if(s.charAt(i)==',' || s.charAt(i)==' ')s.deleteCharAt(i);
        int price = Integer.parseInt(s.toString());

        myCart.setPid(id);
        myCart.setUid(this.userDaoService.getUserByEmail(principal.getName()).getId());
        myCart.setPrice(price);


        boolean f = this.myCartDaoService.savetoMyCart(myCart);
        if(f==true)session.setAttribute("msg", new Message("success", "Item added to cart"));
        else session.setAttribute("msg", new Message("danger", "Something went wrong PLease add again"));

        return "redirect:/user/cart";
    }

    @GetMapping("/{id}/product/{name}")
    public String productOpen(@PathVariable("id")int id, Model model)
    {
        Product product=this.productDaoService.getProductByProductId(id);
        model.addAttribute("product", product);
        return "Normal/ShopNow";
    }

    @GetMapping("/{id}/delete/{name}")
    public String deleteCart(@PathVariable("id")int id)
    {
        System.out.println(id);
        MyCart cart = this.myCartDaoService.getCartByProductId(id);
        System.out.println(cart);
        this.myCartDaoService.deleteFromMyCart(cart);
        return "redirect:/user/cart";
    }

    @GetMapping("/{id}/buy/{name}")
    public String openOrder(@PathVariable("id")int id, Model model, Principal principal)
    {
        model.addAttribute("id", id);
        List<Address> addresses = this.addressDaoService.getAllAddressByUserId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        if(addresses == null)
        return "Normal/Address";
        else 
        {
            model.addAttribute("address", addresses);
            model.addAttribute("id", id);
            return "Normal/Default_Address";
        }
    }

    @PostMapping("/submit-address/{id}")
    public String AddressFormHandler(@PathVariable("id")int id, @ModelAttribute Address address, Principal principal, HttpSession session, Model model)
    {
        address.setUId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        boolean f = this.addressDaoService.saveAddress(address);
        model.addAttribute("id", id);
        if(f==false)
        {
            session.setAttribute("msg", new Message("danger", "Something went wrong !! Please try again"));
        }
        session.setAttribute("msg", new Message("success", "Address saved successfully"));
        model.addAttribute("address", address);
        Product product = this.productDaoService.getProductByProductId(id);
        model.addAttribute("item", product);
        return "Normal/order_confirm";
    }

    @GetMapping("/{id}/address")
    public String addressopener(@PathVariable("id")int id, Model model)
    {
        model.addAttribute("id", id);
        return "Normal/Address";
    }

    @GetMapping("/order_confirm/{id}/{aid}")
    public String orderConfirm(@PathVariable("id")int id, @PathVariable("aid")int aid ,Model model)
    {
        model.addAttribute("order", id);

        Address address = this.addressDaoService.getById(aid);
        model.addAttribute("address", address);

        Product product = this.productDaoService.getProductByProductId(id);
        model.addAttribute("item", product);
        return "Normal/order_confirm";
    }
}
