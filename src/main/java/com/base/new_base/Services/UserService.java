package com.base.new_base.Services;

import com.base.new_base.Converters.OrderConverters;
import com.base.new_base.Converters.UserConverters;
import com.base.new_base.DTO.OrderDTO;
import com.base.new_base.DTO.UserDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.Role;
import com.base.new_base.Entity.User;
import com.base.new_base.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ArrayList<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return new ArrayList<>();
        ArrayList<UserDTO> usersDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = UserConverters.userToUserDto(user);
            usersDTOs.add(userDTO);
        }
        return usersDTOs;
    }


    public UserDTO findById(int id) {
        User user = userRepository.findById(id);
        if (user == null) return null;
        return UserConverters.userToUserDto(user);
    }

    public UserDTO findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null) return null;
        return UserConverters.userToUserDto(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public void save(UserDTO userDTO) {
        if (userDTO.getOrder() != null) for (OrderDTO order : userDTO.getOrder()) order.setUser(userDTO);
        User user = UserConverters.userDtoToUser(userDTO);
        userRepository.save(user);
    }

    public void update(UserDTO userDTO) {
        User user = UserConverters.userDtoToUser(userDTO);
        userRepository.save(user);
    }

    public List<OrderDTO> findOrderByUserId(int id) {
        UserDTO user = findById(id);
        if (user == null || user.getOrder() == null) return new ArrayList<>();
        return user.getOrder();
    }

    public ArrayList<UserDTO> findByRole(Role role) {
        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) return new ArrayList<>();
        ArrayList<UserDTO> usersDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = UserConverters.userToUserDto(user);
            usersDTOs.add(userDTO);
        }
        return usersDTOs;
    }
}
