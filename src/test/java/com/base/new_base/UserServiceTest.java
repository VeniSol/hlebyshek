package com.base.new_base;

import com.base.new_base.Converters.UserConverters;
import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Entity.User;
import com.base.new_base.Services.OrderService;
import com.base.new_base.Services.UserService;
import com.base.new_base.configs.NewBaseApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest(classes = {NewBaseApplication.class})

public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    void getOrder(){
        UserDTO userDTO = userService.findById(11);
        User user = UserConverters.userDtoToUser(userDTO);
        System.out.println(user.getOrders().get(0).getUser());

    }
    @Autowired
    OrderService orderService;
    @Test
    void test(){
        System.out.println(orderService.findAll());
    }


}
