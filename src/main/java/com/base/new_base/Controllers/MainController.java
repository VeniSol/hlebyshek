package com.base.new_base.Controllers;

import com.base.new_base.Cryptography.HashEncoder;
import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.DTO.ProductDTO;
import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.Role;
import com.base.new_base.Repositories.OrderRepository;
import com.base.new_base.Services.OrderService;
import com.base.new_base.Services.ProductService;
import com.base.new_base.Services.SessionService;
import com.base.new_base.Services.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    private final UserService userService;
    private final SessionService sessionService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final HashEncoder hashEncode = new HashEncoder();


    @Autowired
    public MainController(UserService userService, SessionService sessionService, ProductService productService, OrderRepository orderRepository, OrderService orderService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String go(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "main";
    }

    @PostMapping("/login")
    public String loginSent(HttpServletResponse response, @ModelAttribute("user") UserDTO userDTO) {
        UserDTO user = userService.findByLogin(userDTO.getLogin());
        if (user != null) {
            if (user.getPassword().equals(hashEncode.SHA256(userDTO.getPassword()))) {
                setCookie(response, user);
                if(user.getRole()== Role.DELIVERY) return "redirect:/delivery";
                return "redirect:/ordering";
            } else {
                return "redirect:/?error";
            }
        } else {
            return "redirect:/?error";
        }
    }

    @PostMapping("/signup")
    public String signUpSent(@ModelAttribute("user") UserDTO userDTO,HttpServletResponse response,@ModelAttribute("user") UserDTO user) {
        userDTO.setPassword(hashEncode.SHA256(userDTO.getPassword()));
        userDTO.setRole(Role.USER);
        userService.save(userDTO);
        setCookie(response, user);
        return "redirect:/ordering";

    }

    private void setCookie(HttpServletResponse response, UserDTO user){
        Cookie cookie = new Cookie("cyxaruk", user.getLogin());
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }

    @GetMapping("/delivery")
    public String goDeliveryMenu(Model model) {
        return "delivery";
    }

    @GetMapping("/products")
    public String goProducts() {
        return "products";
    }


    @GetMapping("/ordering")
    public String goOrdering(Model model) {
        model.addAttribute("products", productService.findAll());
        return "ordering";
    }

    @PostMapping("/ordering")
    @ResponseBody
    public String sentOrdering(@RequestBody String requestDataList) throws ParseException {
        JSONObject jsonData = (JSONObject) new JSONParser().parse(requestDataList);
        String userLogin = (String) jsonData.get("user");
        String address = (String) jsonData.get("address");
        LocalDateTime currentDateTime = LocalDateTime.now();
        JSONArray quantityArray = (JSONArray) jsonData.get("quant_list");
        for (Object order : quantityArray) {
            JSONObject orderJSON = (JSONObject) order;
            int prodId = (int) (long) orderJSON.get("prodId");
            int quantity = (int) (long) orderJSON.get("quantity");
            productService.updateQuantity(prodId, quantity);
            OrderDTO orderDTO = new OrderDTO(userService.findByLogin(userLogin), productService.findById(prodId), quantity,address,false);
            orderService.save(orderDTO);
        }
        return "{result:'complete'}";
    }

    @GetMapping("/orders")
    public String AllOrders(){
        return "orders";
    }
    @GetMapping("/admin")
    public String goAdminPanel(Model model) {
        ArrayList<ProductDTO> productDTOS = productService.findAll();
        ArrayList<OrderDTO> orderDTOS = orderService.findAll();
        model.addAttribute("products", productDTOS);
        model.addAttribute("orders", orderDTOS);
        return "admin";
    }

    @PostMapping("/admin/editProduct")
    @ResponseBody
    public String sentChangeProducts(@RequestBody String data) throws ParseException {
        ArrayList<Integer> quantityList = new ArrayList<>();
        ArrayList<Double> priceList = new ArrayList<>();
        JSONObject jsonData = (JSONObject) new JSONParser().parse(data);
        for (Object quantityProd : (JSONArray) jsonData.get("quantity")) {
            quantityList.add(Integer.parseInt(String.valueOf(quantityProd)));
        }
        for (Object priceProd : (JSONArray) jsonData.get("price")) {
            priceList.add(Double.parseDouble(String.valueOf(priceProd)));
        }
        productService.updateQuantityAll(quantityList);
        productService.updatePriceAll(priceList);

        return "{result:'complete'}";
    }

    @PostMapping("/admin/editOrder")
    @ResponseBody
    public String sentChangeOrders(@RequestBody String data) throws ParseException {
        ArrayList<Integer> quantityList = new ArrayList<>();
        JSONObject jsonData = (JSONObject) new JSONParser().parse(data);
        for (Object quantityProd : (JSONArray) jsonData.get("quantity")) {
            quantityList.add(Integer.parseInt(String.valueOf(quantityProd)));
        }
        orderService.updateQuantityAll(quantityList);
        return "{result:'complete'}";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        model.addAttribute("activeOrders", orderService.findByUserAndStatus(user,false));
        model.addAttribute("deliveredOrders",  orderService.findByUserAndStatus(user,true));
        model.addAttribute("user", user);
        return "profile";
 }

    @PostMapping("/admin/orderDelete/{id}")
    public String delOrder(@PathVariable int id) {
        orderService.delOrderById(id);
        return "redirect:/admin";
    }

    @PostMapping("/orderDelete/{id}")
    public String deleteOrder(HttpServletRequest request, @PathVariable int id) {
        String name = sessionService.getCookie("cyxaruk", request);
        Order order = orderRepository.findById(id);
        if (order.getUser().getLogin().equals(name)) {
            productService.addQuantity(order.getProduct().getId(), order.getQuantity());
            orderRepository.delete(order);
        }
        return "redirect:/profile";
    }

    @GetMapping("/get-role")
    public String getRole(HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        return user.getRole().toString();
    }
}

