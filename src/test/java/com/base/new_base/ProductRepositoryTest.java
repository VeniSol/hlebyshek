package com.base.new_base;

import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Repositories.UserRepository;
import com.base.new_base.Services.UserService;
import com.base.new_base.configs.NewBaseApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {NewBaseApplication.class})
public class ProductRepositoryTest {
    @Autowired
    UserService userService;
    @Test
    void test(){
        for (OrderDTO orderDTO : userService.findOrderByUserId(11)) {
            System.out.println(orderDTO.getProduct().getNameProd());
        }
    }


}
























