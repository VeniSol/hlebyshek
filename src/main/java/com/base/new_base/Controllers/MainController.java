package com.base.new_base.Controllers;

import com.base.new_base.Cryptography.HashEncoder;
import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.DTO.ProductDTO;
import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.Role;
import com.base.new_base.Entity.Status;
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
                setCookie(response, user.getLogin());
                if (user.getRole() == Role.DELIVERY) return "redirect:/delivery";
                if (user.getRole() == Role.DISPATCHER) return "redirect:/dispatcher";
                return "redirect:/profile";
            } else {
                return "redirect:/?error";
            }
        } else {
            return "redirect:/?error";
        }
    }

    @PostMapping("/signup")
    public String signUpSent(@ModelAttribute("user") UserDTO userDTO, HttpServletResponse response, @ModelAttribute("user") UserDTO user) {
        if (userService.findByLogin(userDTO.getLogin()) != null) {
            return "redirect:/?errorSign";
        }
        userDTO.setPassword(hashEncode.SHA256(userDTO.getPassword()));
        userDTO.setRole(Role.USER);
        userService.save(userDTO);
        setCookie(response, user.getLogin());
        return "redirect:/ordering";

    }

    private void setCookie(HttpServletResponse response, String login) {
        Cookie cookie = new Cookie("cyxaruk", login);
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }


    @GetMapping("/products")
    public String goProducts() {
        return "products";
    }


    @GetMapping("/ordering")
    public String goOrdering(Model model, HttpServletRequest request) {
        model.addAttribute("products", productService.findAll());
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        model.addAttribute("user", user);
        return "ordering";
    }

    @PostMapping("/ordering")
    @ResponseBody
    public String sentOrdering(@RequestBody String requestDataList) throws ParseException {
        JSONObject jsonData = (JSONObject) new JSONParser().parse(requestDataList);
        String userLogin = (String) jsonData.get("user");
        String address = (String) jsonData.get("address");
        JSONArray quantityArray = (JSONArray) jsonData.get("quant_list");
        for (Object order : quantityArray) {
            JSONObject orderJSON = (JSONObject) order;
            int prodId = (int) (long) orderJSON.get("prodId");
            int quantity = (int) (long) orderJSON.get("quantity");
            productService.updateQuantity(prodId, quantity);
            OrderDTO orderDTO = new OrderDTO(userService.findByLogin(userLogin), productService.findById(prodId), quantity, address, Status.ACTIVE);
            orderService.save(orderDTO);
        }
        return "{result:'complete'}";
    }

    @GetMapping("/orders")
    public String AllOrders() {
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

    @GetMapping("/orderHistory")
    public String orderHistory(Model model, HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        model.addAttribute("activeOrders", orderService.findByUserAndStatus(user, Status.ACTIVE));
        model.addAttribute("adoptedOrders", orderService.findByUserAndStatus(user, Status.ADOPTED));
        model.addAttribute("deliveredOrders", orderService.findByUserAndStatus(user, Status.DELIVERED));
        model.addAttribute("user", user);
        model.addAttribute("name",(user.getName()==null||user.getName().isEmpty())?login:user.getName());
        return "orderHistory";
    }

    @PostMapping("/admin/orderDelete/{id}")
    public String delOrder(@PathVariable int id) {
        orderService.delOrderById(id);
        return "redirect:/admin/orders";
    }

    @PostMapping("/orderDelete/{id}")
    public String deleteOrder(HttpServletRequest request, @PathVariable int id) {
        String name = sessionService.getCookie("cyxaruk", request);
        Order order = orderRepository.findById(id);
        if (order.getUser().getLogin().equals(name)) {
            productService.addQuantity(order.getProduct().getId(), order.getQuantity());
            orderRepository.delete(order);
        }
        return "redirect:/orderHistory";
    }

    @GetMapping("/delivery")
    public String goDeliveryMenu(Model model, HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        if (user.getRole() == Role.ADMIN)
            model.addAttribute("adoptedOrders", orderService.findByStatus(Status.ADOPTED));
        else model.addAttribute("adoptedOrders", orderService.findByDelivererAndStatus(login, Status.ADOPTED));
        model.addAttribute("activeOrders", orderService.findByStatus(Status.ACTIVE));
        model.addAttribute("user", user);
        return "delivery";
    }

    @PostMapping("/delivery/setStatusAdopted/{id}")
    public String setStatusAdopted(HttpServletRequest request, @PathVariable int id) {
        String name = sessionService.getCookie("cyxaruk", request);
        Order order = orderRepository.findById(id);
        order.setStatus(Status.ADOPTED);
        order.setDeliverer(name);
        orderService.update(order);
        return "redirect:/delivery";
    }

    @PostMapping("/delivery/setStatusDelivered/{id}")
    public String setStatusDelivered(@PathVariable int id) {
        Order order = orderRepository.findById(id);
        order.setDateTimeOfReceipt(LocalDateTime.now());
        order.setStatus(Status.DELIVERED);
        orderService.update(order);
        return "redirect:/delivery";
    }

    @GetMapping("/admin/orders")
    public String adminOrderList(Model model, HttpServletRequest request) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("users", userService.findAll());
        return "orders";
    }

    @GetMapping("/profile")
    public String goProfile(Model model, HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/saveUserInfo")
    public String saveUserInfo(@ModelAttribute("user") UserDTO updateUser, HttpServletRequest request, HttpServletResponse response) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        if (!login.equals(updateUser.getLogin())) {
            setCookie(response, updateUser.getLogin());
        }
        updateUser.setId(user.getId());
        updateUser.setPassword(user.getPassword());
        updateUser.setRole(user.getRole());
        updateUser.setOrder(user.getOrder());
        updateUser.setAddresses(user.getAddresses());
        userService.update(updateUser);
        return "redirect:/profile";
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestParam("address") String address, HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        user.addAddress(address);
        userService.update(user);

        return "redirect:/profile";
    }

    @PostMapping("delAddress")
    public String delAddress(@RequestParam("companyAddress") String address, HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        user.delAddress(address);
        userService.update(user);
        return "redirect:/profile";
    }

    @PostMapping("/repeatOrder/{id}")
    public String repeatOrder(HttpServletRequest request, @PathVariable int id) {
        String login = sessionService.getCookie("cyxaruk", request);
        Order order = orderRepository.findById(id);
        OrderDTO orderDTO = new OrderDTO(userService.findByLogin(login), productService.findById(order.getProduct().getId()), order.getQuantity(), order.getAddress(), Status.ACTIVE);
        orderService.save(orderDTO);
        return "redirect:/orderHistory";
    }

    @GetMapping("/dispatcher")
    public String dispatcherMenu(Model model, HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        UserDTO user = userService.findByLogin(login);
        model.addAttribute("delivery", userService.findByRole(Role.DELIVERY));
        model.addAttribute("adoptedOrders", orderService.findByStatus(Status.ADOPTED));
        model.addAttribute("activeOrders", orderService.findByStatus(Status.ACTIVE));
        model.addAttribute("user", user);
        return "dispatcher";
    }


}

