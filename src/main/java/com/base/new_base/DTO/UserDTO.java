package com.base.new_base.DTO;

import com.base.new_base.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String login;
    private String password;
    private Role role;
    private List<OrderDTO> order;



    public UserDTO(int id, String login, String password) {
        this.login = login;
        this.password = password;
        this.id = id;
    }


}
