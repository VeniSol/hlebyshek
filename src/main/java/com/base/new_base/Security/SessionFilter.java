package com.base.new_base.Security;

import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Services.SessionService;
import com.base.new_base.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessionFilter extends OncePerRequestFilter {
    final UserService userService;
    final SessionService sessionService;

    @Autowired
    public SessionFilter(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
            String cookie = sessionService.getCookie("cyxaruk",request);
            if (cookie != null) {
                UserDTO user = userService.findByLogin(cookie);
                if (user==null) SecurityContextHolder.clearContext();
                else{

                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            cookie,
                            null,
                            user.getRole().getAuthorities()
                    ));
                }
            }
            else SecurityContextHolder.clearContext();
        try {
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}