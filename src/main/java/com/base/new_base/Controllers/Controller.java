package com.base.new_base.Controllers;

import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Services.SessionService;
import com.base.new_base.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    SessionService sessionService;
    @Autowired
    UserService userService;
    @GetMapping("/get-role")
    public UserDTO getRole(HttpServletRequest request) {
        String login = sessionService.getCookie("cyxaruk", request);
        return userService.findByLogin(login);
    }
}
