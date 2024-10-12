package com.base.new_base;

import com.base.new_base.DTO.ProductDTO;
import com.base.new_base.Entity.Product;
import com.base.new_base.Repositories.ProductRepository;
import com.base.new_base.Services.ProductService;
import com.base.new_base.configs.NewBaseApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@SpringBootTest(classes = {NewBaseApplication.class})
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Test
    void save() {
        ProductDTO product = new ProductDTO();
        product.setNameProd("Богдан73");
        productService.save(product);
        Assertions.assertNotNull(productService.findByNameProd("Богдан73"));
    }
    @Test
    void delete(){
        productService.deleteById(4);
        Assertions.assertNull(productService.findById(4));
    }

}
