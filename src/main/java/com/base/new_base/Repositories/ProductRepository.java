package com.base.new_base.Repositories;

import com.base.new_base.Entity.Product;
import com.base.new_base.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll();

    Product findById(int id);

    Product findByNameProd(String nameProd);


}