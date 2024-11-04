package com.base.new_base.Controllers;

import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.Status;
import com.base.new_base.Repositories.OrderRepository;
import com.base.new_base.Services.OrderService;
import com.base.new_base.Services.SessionService;
import com.base.new_base.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    SessionService sessionService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("/get-role")
    public UserDTO getRole(HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        return userService.findByLogin(login);
    }

    @PostMapping("/dispatcher")
    public OrdersBody updateDeliverer(@RequestBody String requestDataList) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray requestDataArray = (JSONArray) parser.parse(requestDataList);
        for (Object item : requestDataArray) {
            JSONObject jsonData = (JSONObject) item;
            long orderId = Long.parseLong(jsonData.get("orderId").toString());
            String newDeliveryPerson = (String) jsonData.get("deliverer");
            if (newDeliveryPerson != null && !newDeliveryPerson.isEmpty()) {
                Order order = orderRepository.findById(Math.toIntExact(orderId));
                if (order != null) {
                    order.setStatus(Status.ADOPTED);
                    order.setDeliverer(newDeliveryPerson);
                    orderService.update(order);
                }
            }
        }
        return new OrdersBody(orderService.findByStatus(Status.ACTIVE), orderService.findByStatus(Status.ADOPTED));
    }

    public record OrdersBody(List<OrderDTO> activeOrders, List<OrderDTO> adoptedOrders) {
    }
}
