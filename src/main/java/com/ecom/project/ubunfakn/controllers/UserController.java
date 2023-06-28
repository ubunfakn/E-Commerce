package com.ecom.project.ubunfakn.controllers;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecom.project.ubunfakn.entities.*;

import com.ecom.project.ubunfakn.services.*;

import jakarta.servlet.http.*;

import com.ecom.project.ubunfakn.helpers.Message;;



@Controller
@RequestMapping("/user")
public class UserController {
    

    /***********************************Declaration********************************************* */
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

    @Autowired
    OrdersDaoService ordersDaoService;


    /********************************************Functions***************************************** */
    @GetMapping("/mobile")
    public String mobiles(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Mobile");
        List<Product> mobiles=this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Mobiles");
        model.addAttribute("product", mobiles);

        return "Normal/Products";
    }



    @GetMapping("/kitchen")
    public String kitchen(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Kitchen");
        List<Product> kitchen = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Kitchen");
        model.addAttribute("product", kitchen);

        return "Normal/Products";
    }

    @GetMapping("/beauty")
    public String beauty(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Beauty");
        List<Product> beau = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Beauty");
        model.addAttribute("product", beau);

        return "Normal/Products";
    }

    @GetMapping("/fashion")
    public String fashion(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Fashion");
        List<Product> fash=this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Fashion");
        model.addAttribute("product", fash);

        return "Normal/Products";
    }
    @GetMapping("/furniture")
    public String furniture(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Furniture");
        List<Product> furn = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Furniture");
        model.addAttribute("product", furn);

        return "Normal/Products";
    }

    @GetMapping("/computer")
    public String computers(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Computers");
        List<Product> computer = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Computers");
        model.addAttribute("product", computer);

        return "Normal/Products";
    }

    @GetMapping("/electronic")
    public String electronics(Model model)
    {
        Categories categories = this.categoriesDaoService.getCatByName("Electronics");
        List<Product> electronic = this.productDaoService.getByCatId(categories.getId());

        model.addAttribute("title", "Electronics");
        model.addAttribute("product", electronic);

        return "Normal/Products";
    }

    @GetMapping("/")
    public String userHome(Model model,Principal principal)
    {
        User user = this.userDaoService.getUserByEmail(principal.getName());
        if(user.getRole()==Role.ROLE_ADMIN){
            return "redirect:/user/admin/";
        }
        List<Product> products = this.productDaoService.getAllByDiscount(30);
        List<Product> comp = this.productDaoService.getByCat("Computers");
        List<Product> elect = this.productDaoService.getByCat("Electronics");
        List<Product> mobiles = this.productDaoService.getByCat("Mobile");
        List<Product> pro = this.productDaoService.getAllByExactDiscount(60);

        model.addAttribute("product", products.subList(0, 4));
        model.addAttribute("pro", pro.subList(0, 4));
        model.addAttribute("mobile", mobiles.subList(0, 4));
        model.addAttribute("elec", elect.subList(0, 4));
        model.addAttribute("comp", comp.subList(0, 4));
        model.addAttribute("title", "User-Home");

        return "Normal/home";
    }

    @GetMapping("/cart")
    public String cartOpen(Model model, Principal principal)
    {
        List<Integer> pids = this.myCartDaoService.getAllProductId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        List<Product> products=new ArrayList<>();
        List<MyCart> myCarts = this.myCartDaoService.getAllCart();
        long sum=0;

        for(int i=0;i<pids.size();i++)
        {
            Product product = this.productDaoService.getProductByProductId(pids.get(i));
            products.add(product);
        }
        
        for(int i=0;i<myCarts.size();i++)
        {
            if(this.userDaoService.getUserByEmail(principal.getName()).getId()==myCarts.get(i).getUid())
            sum+=myCarts.get(i).getPrice();
        }

        model.addAttribute("title", "My-Cart");
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

            int discount = product.getDiscount();
            int mrp = ((discount*price)/100)+price;

        myCart.setPid(id);
        myCart.setUid(this.userDaoService.getUserByEmail(principal.getName()).getId());
        myCart.setPrice(price);
        myCart.setProductMrp(mrp);


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
        if(addresses.isEmpty())
        {
            model.addAttribute("title", "Add Address Address");
            return "Normal/Address";
        }
        else 
        {
            model.addAttribute("address", addresses);
            model.addAttribute("id", id);
            model.addAttribute("title", "Delivery Address");
            return "Normal/Default_Address";
        }
    }

    @PostMapping("/submit-address/{id}")
    public String AddressFormHandler(@PathVariable("id")int id, @ModelAttribute Address address, Principal principal, HttpSession session, Model model)
    {
        address.setUId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        List<Integer> pids = this.myCartDaoService.getAllProductId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        List<Product> products=new ArrayList<>();
        System.out.println(pids);
        // long sum=0;
        for(int i=0;i<pids.size();i++)
        {
            Product product = this.productDaoService.getProductByProductId(pids.get(i));
            // System.out.println(product);
            products.add(product);
        }
        model.addAttribute("products", products);
        boolean f = this.addressDaoService.saveAddress(address);
        List<MyCart> myCarts = this.myCartDaoService.getAllCart();
        int mrpSum=0;
        for(int i=0;i<myCarts.size();i++)
        {
            if(this.userDaoService.getUserByEmail(principal.getName()).getId()==myCarts.get(i).getUid())
            mrpSum+=myCarts.get(i).getProductMrp();
        }
        List<MyCart> myCartss = this.myCartDaoService.getAllCart();
        int sum=0;
        for(int i=0;i<myCarts.size();i++)
        {
            if(this.userDaoService.getUserByEmail(principal.getName()).getId()==myCarts.get(i).getUid())
            sum+=myCartss.get(i).getPrice();
        }

        model.addAttribute("mrpsum", mrpSum);
        model.addAttribute("sum", sum);

        model.addAttribute("id", id);
        if(f==false)
        {
            session.setAttribute("msg", new Message("danger", "Something went wrong !! Please try again"));
        }
        session.setAttribute("msg", new Message("success", "Address saved successfully"));
        model.addAttribute("address", address);
        Product product = this.productDaoService.getProductByProductId(id);
        model.addAttribute("item", product);
        model.addAttribute("title", "Order");
        if(id==-5)
        {
            for(int i=0;i<products.size();i++)
            {
                Orders orders = new Orders();
                orders.setPaymentStatus("COD");
                orders.setPid(products.get(i).getId());
                orders.setPrice(products.get(i).getPrice());
                orders.setProductMrp(products.get(i).getMrp());
                orders.setUid(this.userDaoService.getUserByEmail(principal.getName()).getId());
                this.ordersDaoService.savetoOrders(orders);
            }
            return "Normal/Cart_Order_Confirm";
        }
        else{
            Orders orders = new Orders();
            orders.setPaymentStatus("COD");
            orders.setPid(product.getId());
            orders.setPrice(product.getPrice());
            orders.setProductMrp(product.getMrp());
            orders.setUid(this.userDaoService.getUserByEmail(principal.getName()).getId());
            this.ordersDaoService.savetoOrders(orders);
            return "Normal/order_confirm";
        }
    }

    @GetMapping("/{id}/address")
    public String addressopener(@PathVariable("id")int id, Model model)
    {
        model.addAttribute("id", id);
        model.addAttribute("title", "Add Address");
        return "Normal/Address";
    }

    @GetMapping("/order_confirm/{id}/{aid}")
    public String orderConfirm(@PathVariable("id")int id, @PathVariable("aid")int aid ,Model model, Principal principal)
    {
        model.addAttribute("order", id);

        List<Integer> pids = this.myCartDaoService.getAllProductId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        List<Product> products=new ArrayList<>();
        for(int i=0;i<pids.size();i++)
        {
            Product product = this.productDaoService.getProductByProductId(pids.get(i));
            // System.out.println(product);
            products.add(product);
        }

        List<MyCart> myCarts = this.myCartDaoService.getAllCart();
        int mrpSum=0;
        for(int i=0;i<myCarts.size();i++)
        {
            if(this.userDaoService.getUserByEmail(principal.getName()).getId()==myCarts.get(i).getUid())
            mrpSum+=myCarts.get(i).getProductMrp();
        }

        Address address = this.addressDaoService.getById(aid);
        Product product = this.productDaoService.getProductByProductId(id);

        List<MyCart> myCartss = this.myCartDaoService.getAllCart();
        int sum=0;
        for(int i=0;i<myCarts.size();i++)
        {
            if(this.userDaoService.getUserByEmail(principal.getName()).getId()==myCarts.get(i).getUid())
            sum+=myCartss.get(i).getPrice();
        }

        model.addAttribute("mrpsum", mrpSum);
        model.addAttribute("address", address);
        model.addAttribute("item", product);
        model.addAttribute("sum", sum);
        model.addAttribute("title", "Order");
        if(id==-5)
        {
            for(int i=0;i<products.size();i++)
            {
                Orders orders = new Orders();
                orders.setPaymentStatus("COD");
                orders.setPid(products.get(i).getId());
                orders.setPrice(products.get(i).getPrice());
                orders.setProductMrp(products.get(i).getMrp());
                orders.setUid(this.userDaoService.getUserByEmail(principal.getName()).getId());
                this.ordersDaoService.savetoOrders(orders);
            }
            model.addAttribute("mrpsum", mrpSum);
            model.addAttribute("address", address);
            model.addAttribute("products", products);
            return "Normal/Cart_Order_Confirm";
        }
        else
        {
            Orders orders = new Orders();
            orders.setPaymentStatus("COD");
            orders.setPid(product.getId());
            orders.setPrice(product.getPrice());
            orders.setProductMrp(product.getMrp());
            orders.setUid(this.userDaoService.getUserByEmail(principal.getName()).getId());
            this.ordersDaoService.savetoOrders(orders);
            return "Normal/order_confirm";
        }
    }

    @GetMapping("/cart/address")
    public String cartrAddress(Model model, Principal principal)
    {
        model.addAttribute("id", 1);
        List<Address> addresses = this.addressDaoService.getAllAddressByUserId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        if(addresses.size()==0)
        return "Normal/Address";
        else 
        {
            System.out.println("address " + addresses.size());
            model.addAttribute("address", addresses);
            model.addAttribute("id", -5);
            model.addAttribute("title", "Delivery Address");
            return "Normal/Default_Address";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id")int aid, HttpServletRequest request, Model model)
    {
        this.addressDaoService.deletAddressById(aid);
        String referer = request.getHeader("Referer");
        model.addAttribute("title", "Delivery Address");
        return "redirect:"+referer;
    }

    @GetMapping("/orders")
    public String orderPage(Model model, Principal principal)
    {
        model.addAttribute("title", "My-Orders");
        List<Integer> pids = this.ordersDaoService.getAllProductId(this.userDaoService.getUserByEmail(principal.getName()).getId());
        List<Product> products=new ArrayList<>();
        for(int i=0;i<pids.size();i++)
        {
            Product product = this.productDaoService.getProductByProductId(pids.get(i));
            // System.out.println(product);
            products.add(product);
        }
        model.addAttribute("product", products);
        return "Normal/Orders";
    }
}
