package com.base.new_base.Converters;

import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.User;

import java.util.List;


public class UserConverters {

    public static User userDtoToUser(UserDTO user) {
        User userConvert = new User();
        userConvert.setId(user.getId());
        userConvert.setLogin(user.getLogin());
        userConvert.setRole(user.getRole());
        userConvert.setPassword(user.getPassword());
        userConvert.setOrders(ordersConnectToUser(OrderConverters.allOrderDtoToOrder(user.getOrder()),userConvert));
        userConvert.setAddresses(user.getAddresses());
        userConvert.setName(user.getName());
        userConvert.setSurname(user.getSurname());
        userConvert.setCompanyName(user.getCompanyName());
        userConvert.setNumber(user.getNumber());
        userConvert.setEmail(user.getEmail());
        return userConvert;
    }

    private static List<Order> ordersConnectToUser(List<Order> orders,User user) {
        for (Order order : orders) order.setUser(user);
        return orders;
    }

    public static UserDTO userToUserDto(User user) {
        UserDTO userConvert = new UserDTO();
        userConvert.setId(user.getId());
        userConvert.setLogin(user.getLogin());
        userConvert.setPassword(user.getPassword());
        userConvert.setRole(user.getRole());
        userConvert.setOrder(OrderConverters.allOrderToOrderDto(user.getOrders()));
        userConvert.setAddresses(user.getAddresses());
        userConvert.setName(user.getName());
        userConvert.setSurname(user.getSurname());
        userConvert.setCompanyName(user.getCompanyName());
        userConvert.setNumber(user.getNumber());
        userConvert.setEmail(user.getEmail());
        return userConvert;
    }
}
