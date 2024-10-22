package com.base.new_base.DTO;

import com.base.new_base.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDTO {
    private UserDTO user;
    private int id;
    private ProductDTO product;
    private int quantity;
    private String address;
    private String dateTime;
    private Boolean status;
    private String dateTimeOfReceipt;

    public OrderDTO(UserDTO user, ProductDTO product, int quantity,String address, Boolean status) {

        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.address = address;
        this.status = status;
    }

}
