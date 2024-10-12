package com.base.new_base.Converters;
import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.User;
import com.base.new_base.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
public class OrderConverters {

    public static Order orderDtoToOrder(OrderDTO order) {
        Order orderConvert = new Order();
        orderConvert.setId(order.getId());
        orderConvert.setQuantity(order.getQuantity());
        orderConvert.setProduct(ProductConverters.productDtoToProduct(order.getProduct()));
        return orderConvert;
    }

    public static OrderDTO orderToOrderDto(Order order) {
        OrderDTO orderConvert = new OrderDTO();
        orderConvert.setId(order.getId());
        orderConvert.setQuantity(order.getQuantity());
        orderConvert.setProduct(ProductConverters.productToProductDto(order.getProduct()));

        return orderConvert;
    }

    public static List<OrderDTO> allOrderToOrderDto(List<Order> orders){
        List<OrderDTO> orderDTOS = new ArrayList<>();
        if (orders!=null) {
            for (Order order : orders) {
                orderDTOS.add(orderToOrderDto(order));
            }
        }
        return orderDTOS;
    }
    public static List<Order> allOrderDtoToOrder(List<OrderDTO> orderDTOS) {
        List<Order> orders = new ArrayList<>();
        if (orderDTOS != null) {
            for (OrderDTO orderDTO : orderDTOS) {
                orders.add(orderDtoToOrder(orderDTO));
            }
        }
        return orders;
    }
}