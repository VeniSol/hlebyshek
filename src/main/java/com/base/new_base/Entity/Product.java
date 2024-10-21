package com.base.new_base.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "products")
public class Product {
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
