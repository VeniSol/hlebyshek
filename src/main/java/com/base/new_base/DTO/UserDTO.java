package com.base.new_base.DTO;

import com.base.new_base.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private Integer id;
    private String login;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String companyName;
    private String number;
    private String email;
    private List<OrderDTO> order;
    private List<String> addresses;

    public UserDTO(int id, String login, String password) {
        this.login = login;
        this.password = password;
        this.id = id;
    }



    public void addAddress(String address) {
        addresses.add(address);
    }


    public void delAddress(String address) {
        addresses.remove(address);
    }
}
