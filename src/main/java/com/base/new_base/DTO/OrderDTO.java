package com.base.new_base.DTO;

import com.base.new_base.Entity.User;

public class OrderDTO {

    private UserDTO user;
    private int id;
    private ProductDTO product;
    private int quantity;

    public OrderDTO(UserDTO user, ProductDTO product, int quantity) {

        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
