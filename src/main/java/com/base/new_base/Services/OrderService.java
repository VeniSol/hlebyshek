package com.base.new_base.Services;

import com.base.new_base.Converters.OrderConverters;
import com.base.new_base.Converters.ProductConverters;
import com.base.new_base.Converters.UserConverters;
import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.DTO.ProductDTO;
import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.Product;
import com.base.new_base.Entity.User;
import com.base.new_base.Repositories.OrderRepository;
import com.base.new_base.Repositories.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;


    public ArrayList<OrderDTO> findAll() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) return new ArrayList<>();
        ArrayList<OrderDTO> ordersDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderConverters.orderToOrderDto(order);
            orderDTO.setUser(UserConverters.userToUserDto(order.getUser()));
            ordersDTOs.add(orderDTO);
        }
        return ordersDTOs;
    }

    public void save(OrderDTO orderDTO) {
        Order order = OrderConverters.orderDtoToOrder(orderDTO);
        order.setUser(UserConverters.userDtoToUser(orderDTO.getUser()));
        order.setOrderDateTime(LocalDateTime.now());
        orderRepository.save(order);
    }

    public void updateQuantityAll(ArrayList<Integer> quantityList) {
        List<Order> orders = orderRepository.findAll();
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setQuantity(quantityList.get(i));
        }
        orderRepository.saveAll(orders);
    }

    public void delOrderById(int id) {
        if (orderRepository.findById(id) != null) orderRepository.deleteById(id);
    }

    public List<OrderDTO> findByUserAndStatus(UserDTO user, boolean status) {
        return OrderConverters.allOrderToOrderDto(orderRepository.findByUserAndStatus(UserConverters.userDtoToUser(user),status));
    }
}
