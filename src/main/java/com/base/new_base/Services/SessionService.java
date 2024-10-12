package com.base.new_base.Services;

import com.base.new_base.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SessionService {

    private final UserService userService;

    @Autowired
    public SessionService(UserService userService) {
        this.userService = userService;
    }

    public boolean isLog(HttpServletRequest request) {
        String cookie=getCookie("cyxaruk",request);
        if (cookie == null) {
            return false;
        }
        return userService.findByLogin(getCookie("cyxaruk",request)) != null;
    }

    public String getCookie(String cookieName,HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    }

