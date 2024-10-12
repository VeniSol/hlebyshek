package com.base.new_base.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    public Product() {}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProd() {
        return nameProd;
    }

    public void setNameProd(String nameProd) {
        this.nameProd = nameProd;
    }

    public Double getPriceProd() {
        return priceProd;
    }

    public void setPriceProd(Double priceProd) {
        this.priceProd = priceProd;
    }
    public int getQuantityProd() {
        return quantityProd;
    }

    public void setQuantityProd(int quantityProd) {
        this.quantityProd = quantityProd;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name_prod")
    private String nameProd;
    @Column(name = "price_prod")
    private Double priceProd;
    @Column(name = "quantity_prod")
    private int quantityProd;
}
